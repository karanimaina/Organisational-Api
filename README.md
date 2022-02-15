# Organisational-Api
this is a REST API thatis used for querying and retrieving Scoped news and information within an organisation . Employees can access general news and departmental news.

## Author
<li> Felix Maina</li>

# Setup and installation 
<li>Fork this repo</li>
<li>Clone this repo</li>
<li>Navigate to the terminal</li>
<li>type in the command git clone and paste the url of clone and then press enter</li>

## to setup the Database
<li>run psql<create.sql</li>

## for local execution</li>
<li>Nvigate to the App class and replace the password and databas name with your credentials, do the same to the test files</li>

## API documentation
### user
<ul>
<li>Creating user https://organisational-news-portal-api.herokuapp.com/users/new
{
"name":"Ruth Mwangi"
"position":"Manager",
"staff_role":"Editor"
}</li>
<li>Viewing User https://organisational-news-portal-api.herokuapp.com/users</li>
<li>Viewing specific User Viewing Specific User https://organisational-news-portal-api.herokuapp.com/user/:id
     Replace :id with id of user
<li>Viewing Specific User Departments https://organisational-news-portal-api.herokuapp.com/users/:id/departments
Replace :id with id of user</li>  

### Departments
<ul>
<li>
Creating Departments https://organisational-news-portal-api.herokuapp.com/departments/new
{
"name":"Editing",
"description":"Editing of books"
}</li>
<li>Viewing Departments https://organisational-news-portal-api.herokuapp.com/departments</li>
<li>Viewing Specific Department https://organisational-news-portal-api.herokuapp.comdepartment/:id
Replace :id with id of department</li>
<li>Viewing Specific User in Departments https://organisational-news-portal-api.herokuapp.com/department/:id/users
Replace :id with id of department</li>
<li>Adding users to department in Departments https://organisational-news-portal-api.herokuapp.com/add/user/:user_id/department/:department_id
Replace :id with id of department</li>
</ul>

### News
<ul>
<li>Creating General News https://organisational-news-portal-api.herokuapp.com/news/new/general
{
"title":"Meeting",
"description":"Discussion about expanding",
"user_id":1
}</li>
<li>Creating Department News https://organisational-news-portal-api.herokuapp.com/news/new/department
{
"title":"Meeting",
"description":"Discussion about expanding",
"department_id":1,
"user_id":1
}
</li>
<li>Viewing general news https://organisational-news-portal-api.herokuapp.com/news/general</li>
<li>Viewing department news https://organisational-news-portal-api.herokuapp.com/news/department/:id</li>
</ul>
 
 ## Technology
 <li>Java</li>
 <li>Postgress</li>
 <li>Heroku</li>
 <li>Postman</li>
 
 ## BDD
  <li>Click on the link :  https://organisational-news-portal-api.herokuapp.com/users/new</li>
  <li>copy the url  and place it in the postman url bar,Navigate to the post man and select post verb, </li>
  <li>on the body  rab select raw and  type json </li>
  <li>pass this data
  {
"name":"Ruth Mwangi"
"position":"Manager",
"staff_role":"Editor"
}
  </li>
<li>click on send , you get an output similar to what you have typed with the  given Id</li>
<li></li>
<li>you can query by passing the Id generated ,replace the is with the number provided, https://organisational-news-portal-api.herokuapp.com/user/:id</li>

## Contributions
for any queries , contributions to the project feel free to reach out  @karani.maina2010@gmail.com
  
  
  






