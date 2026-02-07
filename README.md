# RevShop - E-Commerce Application

RevShop is a console-based e-commerce application built with Java and JDBC. It supports distinct workflows for Buyers and Sellers.

## Technology Stack
-   **Language**: Java (JDK 8+)
-   **Database**: Oracle Database (JDBC)
-   **Logging**: Java Util Logging (Custom LoggerUtil)

## Setup Instructions
1.  **Database**: Execute the provided SQL scripts (ERD based) to set up tables (`users`, `products`, `orders`, `carts`, `sellers`).
2.  **Configuration**: Update `src/com/revshop/util/JDBCUtil.java` with your database credentials.
3.  **Compile**:
    ```bash
    javac -d bin -sourcepath src src/com/revshop/main/Main.java
    ```
4.  **Run**:
    ```bash
    java -cp bin com.revshop.main.Main
    ```

## Usage Flow
-   **Registration**: Users can register as "Buyer" or "Seller". Sellers must provide business details.
-   **Buyer Flow**: Browse products, search by category, add to cart (modify quantity), manage favorites, checkout (place order), write reviews.
-   **Seller Flow**: Add products, view inventory, update stock.

## Database Design (ER Diagram)
```mermaid
erDiagram
    USERS {
        int user_id PK
        string email
        string role
    }
    SELLERS {
        int seller_id PK
        int user_id FK
        string business_name
    }
    PRODUCTS {
        int product_id PK
        int seller_id FK
        double price
        int stock
    }
    CARTS {
        int cart_id PK
        int user_id FK
    }
    CART_ITEMS {
        int cart_item_id PK
        int cart_id FK
        int product_id FK
    }
    ORDERS {
        int order_id PK
        int user_id FK
        double total_amount
        string status
    }
    ORDER_ITEMS {
        int order_item_id PK
        int order_id FK
        int product_id FK
        double price
    }
    REVIEWS {
        int review_id PK
        int product_id FK
        int user_id FK
        int rating
        string comment
    }
    FAVORITES {
        int favorite_id PK
        int user_id FK
        int product_id FK
    }

    USERS ||--o| HELP_CENTER : "ticks" (Support)
    USERS ||--o| SELLERS : "is a"
    SELLERS ||--o{ PRODUCTS : manages
    USERS ||--o| CARTS : has
    CARTS ||--o{ CART_ITEMS : contains
    PRODUCTS ||--o{ CART_ITEMS : in
    USERS ||--o{ ORDERS : places
    ORDERS ||--o{ ORDER_ITEMS : contains
    USERS ||--o{ REVIEWS : writes
    PRODUCTS ||--o{ REVIEWS : receives
    USERS ||--o{ FAVORITES : saves
```
