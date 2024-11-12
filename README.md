Приложение "CRUDServlet".  
REST приложение с использованием сервлетов, JDBC:  

# API requests
Запросы:
Company:
GET  http://localhost:8080/companies - получить все компании\
GET  http://localhost:8080/companies/{id} - получить компанию по id\
POST http://localhost:8080/companies - добавить новую компанию\
PUT  http://localhost:8080/companies/{id} - изменить компанию по id\
DELETE http://localhost:8080/companies/{id} - удалить компанию по id\
\
Employee:\
GET  http://localhost:8080/employees - получить всех работников\
GET  http://localhost:8080/employees/{id} - получить работника по id\
POST http://localhost:8080/employees - добавить нового работника\
PUT  http://localhost:8080/employees/{id} - изменить работника по id\
DELETE http://localhost:8080/employees/{id} - удалить компанию по id\
\
Task\:
GET  http://localhost:8080/tasks - получить все задания\
GET  http://localhost:8080/tasks?companyId={id} - получить все задания от компании по id\
GET  http://localhost:8080/tasks?employeeId={id} - получить все задания работника по id\
POST http://localhost:8080/tasks - добавить новое задание\
DELETE http://localhost:8080/tasks/{id} - удалить задание по id\
PUT  http://localhost:8080/tasks/{id}/company/{id} - назначить компанию заданию\
PUT  http://localhost:8080/tasks/{id}/employee/{id} - назначить работнику задание\
