# n11-talenthub-bootcamp-graduation-project


<!-- PROJECT LOGO -->
<br />
<div align="left">

<h3 align="center">Credit Application</h3>





<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <a href="#project-include">Project Include</a>
      <a href="#project-screens">Project Screenshoots</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>

  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
This project is a credit application project.

Project Description:
* In this project, a customer can be registered, the record can be deleted, the record can be updated and all customers can be displayed on the screen.
* A registered customer can apply for a credit.
* A customer who is not registered in the system cannot apply for a credit.
* As a result of this credit application, the result is returned to the customer whether the credit has been approved and, if approved, how much credit limit the customer can get.
* Credit application is made according to the registered customer's T.R. ID number.
* A registration with a credit application can be queried with the customer's T.R. ID number and birthday
* A customer can only apply for a credit once. If customer applies for a credit again, customer will receive an error "There is already a registered credit application"

## Project Screen Shoots

<p align="center">Customer Add</p>
<p align="center">
<img src="https://github.com/n11-TalentHub-Java-Bootcamp/n11-talenthub-bootcamp-graduation-project-serkanbodur/blob/main/n11-graduation-project/images/add-customer.PNG" alt="Logo" width="800" height="600">
</p>

<p align="center">Credit Apply</p>
<p align="center">
<img src="https://github.com/n11-TalentHub-Java-Bootcamp/n11-talenthub-bootcamp-graduation-project-serkanbodur/blob/main/n11-graduation-project/images/credit-apply.PNG" alt="Logo" width="800" height="600">
</p>

<p align="center">Credit Result</p>
<p align="center">
<img src="https://github.com/n11-TalentHub-Java-Bootcamp/n11-talenthub-bootcamp-graduation-project-serkanbodur/blob/main/n11-graduation-project/images/credit-result.PNG" alt="Logo" width="800" height="600">
</p>

<p align="center">Credit Apply Result</p>
<p align="center">
<img src="https://github.com/n11-TalentHub-Java-Bootcamp/n11-talenthub-bootcamp-graduation-project-serkanbodur/blob/main/n11-graduation-project/images/creidt-apply-result.PNG" alt="Logo" width="800" height="300">
</p>

<p align="center">Credit Query Result</p>
<p align="center">
<img src="https://github.com/n11-TalentHub-Java-Bootcamp/n11-talenthub-bootcamp-graduation-project-serkanbodur/blob/main/n11-graduation-project/images/credit-query-result.PNG" alt="Logo" width="800" height="300">
</p>


### Built With

* [Java](https://www.java.com/tr/)
* [Spring](https://spring.io/)
* [React.js](https://reactjs.org/)
* [Docker](https://www.docker.com/)
* [Bootstrap](https://getbootstrap.com)
* [Postgre](https://www.postgresql.org/)

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- PROJECT INCLUDE -->
## Project Include
1. Java Spring is used for project backend
2. React is used project frontend
3. The tests were written using JUnit
4. The project was documented using Swagger
5. Project logged using Slf4j;

<!-- GETTING STARTED -->
## Getting Started

### Installation


1. Clone the repo
   ```sh
   git clone https://github.com/n11-TalentHub-Java-Bootcamp/n11-talenthub-bootcamp-graduation-project-serkanbodur.git
   ```
2. Install NPM packages
   ```sh
      
   npm install react
   npm install bootstrap
   npm install antd
   npm install react-router-dom
   
   ```



<!-- USAGE EXAMPLES -->
## Usage

- Run project once to create the tables and ddl operations
- Apply the operations according to appropriate endpoints using Postman or Swagger.

[Customer Paths](n11-talenthub-bootcamp-graduation-project-serkanbodur/n11-graduation-project/src/main/java/com/example/n11graduationproject/controller/CustomerController.java)

| Request Method | Route                                 | Request Body                                                                                                                                                                                   | Description                                |
|----------------|---------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
|       GET      | /api/v1/customers/                    | {  }                                                                                                                                                                                           | Get all Customers                          |
|      POST      | /api/v1/customers/                    | {  "idNumber" : "12345678, ""name" : "test_user", "surname" : "test_user_sur", "phone" : "2233" , "monthlyIncome" : 5000.0, "dateOfBirth" : "2000-01-01", "guranteeFee" : "10000" }            | Save a Customer                            |
|       PUT      | /api/v1/customers/{id}                | {  "idNumber" : "12345678, ""name" : "updated_user", "surname" : "test_user_sur", "phone" : "2233" , "monthlyIncome" : 5000.0, "dateOfBirth" : "2000-01-01", "guranteeFee" : "10000" }         | Update a Customer using id                 |
|     DELETE     | /api/v1/customers/                    | {  }                                                                                                                                                                                           | Delete a Customer using id                 |


[Credit Paths](n11-talenthub-bootcamp-graduation-project-serkanbodur/n11-graduation-project/src/main/java/com/example/n11graduationproject/controller/CreditController.java)

| Request Method | Route                                | Request Body | Description                                        |
|----------------|--------------------------------------|--------------|----------------------------------------------------|
|       GET      | /api/v1/credits/                     | {  }         | Get a credit using customer idNumber and birthdate |
|      POST      | /api/v1/credits/                     | {  }         | Save a credit using customer idNumber              |   



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Serkan Bodur

* [sbodur25@gmail.com](sbodur25@gmail.com)
* [Linkedln](https://tr.linkedin.com/in/serkan-bodur)


Project Link: [https://github.com/n11-TalentHub-Java-Bootcamp/n11-talenthub-bootcamp-graduation-project-serkanbodur.git)

<p align="right">(<a href="#top">back to top</a>)</p>



