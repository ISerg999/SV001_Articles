# SV001_Articles

В файле application.properties указывается ссылка на таблицу баз данных:
spring.datasource.url=jdbc:h2:file:~/db/articles.db
Либо исправте для себя, либо закомментируйте её и раскомментируйте
#spring.datasource.url=jdbc:h2:mem:articles_db
Тогда база данных будет создаваться в памяти.
