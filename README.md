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
<li>Creating user 
{
"name":"Ruth Mwangi"
"position":"Manager",
"staff_role":"Editor"
}</li>
<li>Viewing User </li>
<li>Viewing specific User Viewing Specific User 
     Replace :id with id of user
<li>Viewing Specific User Departments 
Replace :id with id of user</li>  

### Departments
<ul>
<li>
Creating Departments 
{
"name":"Editing",
"description":"Editing of books"
}</li>
<li>Viewing Departments</li>
<li>Viewing Specific Department 
Replace :id with id of department</li>
<li>Viewing Specific User in Departments
Replace :id with id of department</li>
<li>Adding users to department in Departments
Replace :id with id of department</li>
</ul>

### News
<ul>
<li>Creating General News 
{
"title":"Meeting",
"description":"Discussion about expanding",
"user_id":1
}</li>
<li>Creating Department News 
{
"title":"Meeting",
"description":"Discussion about expanding",
"department_id":1,
"user_id":1
}
</li>
<li>Viewing general news </li>
<li>Viewing department news </li>
</ul>
 
 ## Technology
 <li>Java</li>
 <li>Postgress</li>
 <li>Heroku</li>
 <li>Postman</li>
 
 ## BDD
  <li>Click on the link :  </li>
  <li>copy the url  and place it in the postman url bar,Navigate to the post man and select post verb, </li>
  <li>on the body  rab select raw and  type json </li>
  <li>pass this data
  {
"name":"Felix maina"
"position":"Manager",
"staff_role":"Editor"
}
  </li>
<li>click on send , you get an output similar to what you have typed with the  given Id</li>
<li></li>
<li>you can query by passing the Id generated ,replace the is with the number provided, /li>

## Contributions
for any queries , contributions to the project feel free to reach out  @karani.maina2010@gmail.com
 
## License
### License

MIT License

Copyright (c) 2022 Felix Maina

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


  
  
  






