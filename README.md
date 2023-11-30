# <h1 align="center">  Book Online Service  </h1>
#### <h4 align="center"> `Fast and convenient` </h4>

### <h2 align="center"> Introduction </h2>
`The Book Online Service aims to provide an efficient and user-friendly platform for online book store. It caters to both customers and administrators, allowing customers to browse books, manage their shopping carts, place orders, and review their order history, while administrators can manage the book catalog, categories, and monitor order statuses.`

### <h2 align="center"> Functionalities </h2>

### For Shoppers
- **Join and sign in**: Register and sign in to access books and make purchases.
- **Explore bookshelf sections**: Access book categories and view books within each section.
- **Use the shopping cart**: Add, view, and remove items.
- **Purchase books**: Buy items in the shopping cart.
- **View past receipts**: Review previous purchase details.

### For Managers
- **Arrange books**: Add, modify, or remove books from the store.
- **Organize bookshelf sections**: Create, update, or remove book categories.
- **Manage receipts**: Update receipt statuses (e.g., "Shipped" or "Delivered").

### <h2 align="center"> Technologies Used </h2>
`The following technologies are used to build the Book Online Service:`
- <img src="https://image.emojipng.com/677/13219677.jpg" width="30"/> **Java**: The primary programming language used for the application.
- <img src="https://media.trustradius.com/product-logos/9B/8G/IMJEF6VWC74S.PNG" width="30"/> **Spring Boot**: A powerful framework that provides essential features for building web applications.
- <img src="https://media.trustradius.com/product-logos/9B/8G/IMJEF6VWC74S.PNG" width="30"/> **Spring Data JPA**: Simplifies data access and persistence with JPA (Java Persistence API).
- <img src="https://media.trustradius.com/product-logos/9B/8G/IMJEF6VWC74S.PNG" width="30"/> **Spring Security**: Enables robust and secure authentication and authorization mechanisms.
- <img src="https://t1.gstatic.com/images?q=tbn:ANd9GcSPbQehl7jW6cT9MZXqOeA4FNWqVNkOThwpkukkugx0lD1EhpIH" width="30"/> **Swagger**: Provides API documentation.
- <img src="https://w7.pngwing.com/pngs/464/18/png-transparent-mysql-database-innodb-postgresql-column-marine-mammal-electric-blue-postgresql-thumbnail.png" width="30"/> **MySQL**: The database management system used for data storage.
- <img src="https://velog.velcdn.com/images/gloom/post/17bae182-7380-43e0-a45e-fff76b8ba9c7/image.png" width="35"/> **Lombok**: Reduces boilerplate code with annotations.
- <img src="https://trguduru.github.io/img/mapstruct.png" width="35"/> **MapStruct**: Simplifies object mapping between DTOs and entities.


###<h2 align="center"> The follow steps below to install </h2>
1. Clone the repository: `git clone https://github.com/evgenkolner/BookOnlineService.git`
2. Navigate to the project directory: `cd BookOnlineService`
3. Build the project using Maven: `mvn clean install`
4. Run the application: `mvn spring-boot:run`


## <img src="https://em-content.zobj.net/thumbs/160/apple/354/link_1f517.png" width="25"/> API Endpoints
`The Book Online Service provides the following API endpoints:`

### **Authentication Controller:**

| **HTTP method** | **Endpoint**  | **Role** | **Function** |
|:----------------:|:--------------:|:--------:|:-------------|
| POST | /register | ALL | Allows users to register a new account. |
| POST | /login | ALL | Get JWT tokens for authentication. |

---

### **Book Controller:** _Searching for books (CRUD for books)_

| **HTTP method** | **Endpoint**  | **Role** | **Function**                       |
|:---------------:|:-------------:|:--------:|:-----------------------------------|
|       GET       |    /books     |   USER   | Endpoints for managing books.      |
|       GET       |  /books/{id}  |   USER   | Search for a specific book by id.  |
|       PUT       | /{books}/{id} |  ADMIN   | Allows admin to update book by id. |
|      POST       |   /{books}    |  ADMIN   | Allows admin to create new book.   |
|     DELETE      | /{books}/{id} |  ADMIN   | Allows admin to delete book.       |

---

### **Category Controller:** _Managing category (CRUD for Categories)_

| **HTTP method** |   **Endpoint**    | **Role** | **Function**                          |
|:--------------:|:-----------------:|:--------:|:--------------------------------------|
|      POST      |     /category     |  ADMIN   | Allow admin to create a new category. |
|       GET      |     /category     |   USER   | Get all categories from DB.           |
|       GET      |  /category/{id}   |   USER   | Get category by id from DB.           |
|       PUT      |  /category/{id}   |  ADMIN   | Update category by id.                |
|    DELETE      |  /category/{id}   |  ADMIN   | Allow admin delete some category.     |

---

### **Cart Controller:** _User cart management_

| **HTTP method** |    **Endpoint**     | **Role** | **Function**                                   |
|:---------------:|:-------------------:|:--------:|:-----------------------------------------------|
|       GET       |        /cart        |   USER   | Get cart from logged user from DB.             |
|      POST       |        /cart        |   USER   | Add books to the user cart".                   |
|     DELETE      |  /cart-items/{id}   |   USER   | Delete cart item from the user cart.           |
|       PUT       |  /cart-items/{id}   |   USER   | Update quantity of cart item in the user cart. |

---

### **Order Controller:** _Managing orders_

| **HTTP method** |           **Endpoint**           | **Role** | **Function**                         |
|:--------------:|:--------------------------------:|:--------:|:-------------------------------------|
|      POST      |             /orders              |   USER   | Allow user to make new order.        |
|       PUT      |        /orders/{orderId}         |  ADMIN   | Allows admin to update order status. |
|       GET      |             /orders              |   USER   | Get order history.                   |
|       GET      | /orders/{orderId}/items/{itemId} |   USER   | Get item from order.                 |
|       GET      |     /orders/{orderId}/items      |   USER   | Get all items from order.            |

--

### <h2 align="center"> Contacts </h2>

`For additional information or inquiries, please contact:`
- Email: evgen.kolner@gmail.com
- [LinkedIn](https://www.linkedin.com/in/ievgenii-kolner)
