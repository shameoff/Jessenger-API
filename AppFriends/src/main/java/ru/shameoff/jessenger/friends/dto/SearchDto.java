package ru.shameoff.jessenger.friends.dto;

import java.util.Date;

/*
Принимает в теле запроса набор параметров из сущности друзья и осуществляет поиск по произвольному набору этих
параметров. Как пример, мы решили искать по дате добавления, присылаем только эту самую дату и метод должен
осуществить запрос в БД только по этому полю, полностью игнорируя остальные
 @Id
    @NonNull
    @Column(name = "id", columnDefinition = "VARCHAR(255)", nullable = false)
    private String id;
    @NonNull
    @Column(nullable = false)
    private String userId;
    @NonNull
    @Column(nullable = false)
    private String friendId;
    @NonNull
    @Column(nullable = false)
    private String friendFullName;
    @Temporal(TemporalType.DATE)
    @Column
    private Date added_at;
    @Temporal(TemporalType.DATE)
    @Column
    private Date deleted_at;

 */
public class SearchDto {

    private String name;

    private Date added_at;

    private Date deleted_at;

}
