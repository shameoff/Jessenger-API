package ru.shameoff.jessenger.chat.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shameoff.jessenger.chat.dto.*;
import ru.shameoff.jessenger.chat.service.ChatService;
import ru.shameoff.jessenger.common.exception.BadRequestException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "Отправляет сообщение в чат или пользователю, в зависимости от того, что указано в передаваемом dto")
    @PostMapping("/message")
    public void sendMessage(@Valid @RequestBody NewMessageDto messageDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(printErrors(bindingResult));
        }
        if (!(messageDto.getReceiverId() == null
                && messageDto.getChatId() != null
                || messageDto.getReceiverId() != null
                && messageDto.getChatId() == null)) {
            bindingResult.reject("fields.invalid",
                    "Одно и только одно из полей usedId или chatId должно быть заполнено корректным UUID");
            throw new BadRequestException(printErrors(bindingResult));
        } else if (messageDto.getChatId() != null) {
            chatService.sendMessage(messageDto);

        } else {
            chatService.sendPrivateMessage(messageDto);
        }
    }

    @Operation(summary = "Создаёт неприватный чат с указанными параметрами")
    @PostMapping("")
    public ChatInfoDto createChat(@Valid @RequestBody ChatCreationDto chatDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(printErrors(bindingResult));
        }
        return chatService.createChat(chatDto);
    }

    @Operation(summary = "Изменяет информацию об указанном чате, но не позволяет удалять участников")
    @PutMapping("")
    public ChatInfoDto editChat(@Valid @RequestBody ChatChangeDto chatChangeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(printErrors(bindingResult));
        }
        return chatService.editChat(chatChangeDto);
    }

    @Operation(summary = "Возвращает информацию о запрашиваемом чате")
    @GetMapping("/{chatId}")
    public ChatInfoDto retrieveChatInformation(@PathVariable UUID chatId) {
        return chatService.retrieveChatInformation(chatId);
    }

    @Operation(summary = "Возвращает сообщения из указанного чата")
    @GetMapping("/{chatId}/messages")
    public List<MessageInListDto> retrieveChatMessages(@PathVariable UUID chatId) {
        return chatService.retrieveChatMessages(chatId);
    }

    @Operation(summary = "Возвращает список чатов авторизованного пользователя")
    @GetMapping("")
    public List<ChatInfoDto> retrieveChats() {
        return chatService.retrieveChats();
    }

    @Operation(summary = "Выполняет поиск сообщений по переданным параметрам")
    @GetMapping("/search")
    public List<MessageInListDto> findMessages(FindMessagesDto dto) {
        return chatService.findMessages(dto);
    }


    /**
     * Функция, которая собирает все ошибки в одну строку
     * @param bindingResult an object where error logs located
     * @return a string with all errors combined
     */
    private String printErrors(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        bindingResult.getFieldErrors().
                forEach(error -> errorMessage.append(String.format("Field %s is required%n", error.getField())));
        return errorMessage.toString();
    }
}