# java-explore-with-me
Ссылка на пул реквест:
<a>https://github.com/AleksandrDorofeev23/java-explore-with-me/pull/5

# Фича - комментарии

### Эндпоинты:

#### Административные:

DELETE &nbsp; &nbsp; http://localhost:8080/admin/comments/{commentId} <br> - позволяет администрации удалить любой комментарий

PATCH &nbsp; &nbsp; http://localhost:8080/admin/comments/{userId} - позволяет администрации заблокировать пользователю возможность писать и редактировать комментарии на выбранное количество дней. По умолчанию - 1 день

#### Привантные:

POST &nbsp; &nbsp; http://localhost:8080/users/{userId}/comments?eventId={} <br> - позволяет создавать комментарии к опубликованным событиям

GET &nbsp; &nbsp; http://localhost:8080/users/{userId}/comments - позволяет пользователю получить все свои комментарии с пагинацией

PATCH &nbsp; &nbsp; http://localhost:8080/users/{userId}/comments/{commentId} <br> - позволяет пользователю редактировать комментарий в течение суток

DELETE &nbsp; &nbsp; http://localhost:8080/users/{userId}/comments/{commentId} <br> - позволяет пользователю удалить комментарий

#### Публичные:

GET &nbsp; &nbsp; http://localhost:8080/comments/{commentId} - позволяет получить комментарий по id

GET &nbsp; &nbsp; http://localhost:8080/comments/all/{eventId} - позволяет получить все комментарии к конкретному событию

![img](https://github.com/{username}/{repository}/raw/main/{path}/img.png)

