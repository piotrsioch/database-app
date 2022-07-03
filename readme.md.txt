# Application Info
This is an application, which allows you to menage your week. After logging in you have three possibilities - you can use calendar, diary or medicine tab in dashboard. Application is using SQL database, so you have to install one of its distributions. I recommend using mySQL. 
I have added a script, which creates database with tables, which are required to run this application. After running the script, you need to put valid data in 
src/sample/DatabaseConnection/DatabaseConnection - databaseName, databaseUser and databasePassword. 

![image](https://user-images.githubusercontent.com/93688242/176809573-b9aa0eaf-8a0c-4eaf-ae28-c9bb58c87d9e.png)


Addictionaly, you have to add sql connector to external libraries, it is included in repository. If you are using other distribution of SQL, you need to download
different driver.

# Launching application

![image](https://user-images.githubusercontent.com/93688242/176809844-05f44a8b-49fa-4bc8-a614-88409510c170.png)

When you first launch this applicationm you need to create new account, and put there your credentials. They will be subject to simple validation. After creating 
an account you can log in.

![image](https://user-images.githubusercontent.com/93688242/177059110-e8f909df-c893-46f4-add9-5c18506a720b.png)

# Logged In

 Currently there are three possiblities - you can add an event in a calendar, add an event in a diary and add medicine in medecines. Everything is editable - you can
 add new things and delete them. You can see status of taking your medicines - if you have taken a medicine on given day (you selected a medicine and clicked 'take'
 button) the status is 'taken'. Everyday it is reseted. 

![image](https://user-images.githubusercontent.com/93688242/177059146-f37abda1-98cc-4bd9-8a89-6f636120becb.png)

![image](https://user-images.githubusercontent.com/93688242/177059247-0a75ead4-0adf-45a5-a2ee-12a1d0a1e249.png)


![image](https://user-images.githubusercontent.com/93688242/177059180-900e07c8-428a-48be-8c68-bf84063cc027.png)

![image](https://user-images.githubusercontent.com/93688242/177059188-1c95bf56-ea84-4ab7-a335-1fad01ed2309.png)





