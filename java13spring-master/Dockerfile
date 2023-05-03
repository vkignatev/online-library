#образ взятый за основу
FROM openjdk:17
#Записываем в переменную путь до варника (необязательно)
ARG jarFile=target/Java13SpringProject-0.1.war
#Куда переместить варник внутри контейнера
WORKDIR /opt/app
#копируем наш джарник в новый внутри контейнера
COPY ${jarFile} library.war
#Открываем порт
EXPOSE 9090
#Команда для запуска
ENTRYPOINT ["java", "-jar", "library.war"]
