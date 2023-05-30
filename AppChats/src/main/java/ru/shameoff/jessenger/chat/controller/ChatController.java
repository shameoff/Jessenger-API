package ru.shameoff.jessenger.chat.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.chat.dto.ChatChangeDto;
import ru.shameoff.jessenger.chat.dto.ChatCreationDto;
import ru.shameoff.jessenger.chat.dto.NewMessageDto;
import ru.shameoff.jessenger.chat.service.ChatService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "Отправляет сообщение в чат или пользователю, в зависимости от того, что указано в передаваемом dto")
    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody NewMessageDto messageDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(printErrors(bindingResult));
        }
        if (!hasOneNonNullField(messageDto)) {
            bindingResult.reject("fields.invalid",
                    "Одно и только одно из полей usedId или chatId должно быть заполнено корректным UUID");
            return ResponseEntity.badRequest().body(printErrors(bindingResult));
        } else if (messageDto.getChatId() != null) {
            return chatService.sendMessage(messageDto);

        }
        else if (messageDto.getReceiverId() != null){
            return chatService.sendPrivateMessage(messageDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Создаёт неприватный чат с указанными параметрами")
    @PostMapping("")
    public ResponseEntity<?> createChat(@Valid @RequestBody ChatCreationDto chatDto, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? ResponseEntity.badRequest().body(printErrors(bindingResult))
                : chatService.createChat(chatDto);
    }

    @Operation(summary = "Изменяет информацию об указанном чате, но не позволяет удалять участников")
    @PutMapping("")
    public ResponseEntity<?> editChat(@Valid @RequestBody ChatChangeDto chatChangeDto, BindingResult bindingResult) {
        return bindingResult.hasErrors() ? ResponseEntity.badRequest().body(printErrors(bindingResult))
                : chatService.editChat(chatChangeDto);
    }

    @Operation(summary = "Возвращает информацию о запрашиваемом чате")
    @GetMapping("/{chatId}")
    public ResponseEntity<?> retrieveChatInformation(@PathVariable UUID chatId) {
        return chatService.retrieveChatInformation(chatId);
    }

    @Operation(summary = "Возвращает сообщения из указанного чата")
    @GetMapping("/{chatId}/messages")
    public ResponseEntity<?> retrieveChatMessages(@PathVariable UUID chatId) {
        return chatService.retrieveChatMessages(chatId);
    }

    @Operation(summary = "Возвращает список чатов авторизованного пользователя")
    @GetMapping("")
    public ResponseEntity<?> retrieveChats() {
        return chatService.retrieveChats();
    }

    @Operation(summary = "Выполняет поиск сообщений по переданным параметрам")
    @GetMapping("/search")
    public ResponseEntity<?> findMessages() {
        return chatService.findMessages();
    }


    /**
     * Function that combines all errors from invalid input in one string
     *
     * @param bindingResult an object where error logs located
     * @return a string with all errors combined
     */
    private String printErrors(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        bindingResult.getFieldErrors().forEach(error -> errorMessage.append(String.format("Field %s is required%n", error.getField())));
        return errorMessage.toString();
        // Тут бы по-хорошему возвращать не просто сообщение, а ошибку в JSON, но потом.
    }

    /**
     * Проверяет, что в DTO одно и только одно поле из userId и chatId не нулевое
     *
     * @param messageDto
     * @return true, если одно и только одно поле из userId и chatId не нулевое, иначе false
     */
    private boolean hasOneNonNullField(NewMessageDto messageDto) {
        return messageDto.getReceiverId() == null && messageDto.getChatId() != null
                || messageDto.getReceiverId() != null && messageDto.getChatId() == null;
    }

}
