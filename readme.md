# Food Express

Food Express is a food delivery service platform that connects users with local restaurants and food vendors. It allows users to browse menus, place orders, and have their favorite meals delivered to their doorstep. The platform is designed to be user-friendly, efficient, and scalable.

## Features

- **User Authentication:** Secure login and registration for users.
- **Browse Restaurants:** Explore a variety of restaurants and their menus.
- **Place Orders:** Easy and quick order placement.
- **Order Tracking:** Real-time tracking of orders.
- **Payment Integration:** Secure payment processing.
- **Admin Dashboard:** Manage restaurants, orders, and users.
- **Responsive Design:** Optimized for both desktop and mobile devices.

## Installation

To run Food Express locally, follow these steps:

1. **Clone the Repository:**
    ```sh
    git clone https://github.com/Aliabbos-Ashurov/Food-Express.git
    cd Food-Express
    ```

2. **Install Dependencies:**
    ```sh
    npm install
    ```

3. **Set Up Environment Variables:**
   Create a `.env` file in the root directory and add the following variables:
    ```sh
    DATABASE_URL=<your-database-url>
    SECRET_KEY=<your-secret-key>
    ```

4. **Run Database Migrations:**
    ```sh
    npx sequelize-cli db:migrate
    ```

5. **Start the Development Server:**
    ```sh
    npm start
    ```

The application should now be running on `http://localhost:3000`.

## Usage

1. **Sign Up:** Create a new account or log in if you already have one.
2. **Browse Restaurants:** Navigate through the list of available restaurants.
3. **Place an Order:** Select your favorite dishes and place an order.
4. **Track Your Order:** Monitor the status of your order in real-time.
5. **Admin Features:** If you have admin access, manage the restaurants, orders, and users through the admin dashboard.

## Contributing

We welcome contributions to Food Express! To contribute, follow these steps:

1. **Fork the Repository:**
   Click the "Fork" button at the top right of this page.

2. **Clone Your Fork:**
    ```sh
    git clone https://github.com/<your-username>/Food-Express.git
    cd Food-Express
    ```

3. **Create a Branch:**
    ```sh
    git checkout -b feature/your-feature-name
    ```

4. **Make Your Changes:**
   Implement your feature or bug fix.

5. **Commit Your Changes:**
    ```sh
    git add .
    git commit -m "Add your commit message here"
    ```

6. **Push to Your Fork:**
    ```sh
    git push origin feature/your-feature-name
    ```

7. **Create a Pull Request:**
   Go to the original repository and create a new pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

## Contact

For any inquiries or support, please contact:

- **Aliabbos Ashurov**
- **Doniyor Nishonov**
- **Email:** [aliabbos@example.com](mailto:aliabbos@example.com)
- **Email:** [nishonovd80@gmail.com](mailto:nishonovd80@gmail.com)
- **GitHub:** [Aliabbos-Ashurov](https://github.com/Aliabbos-Ashurov)
- **GitHub:** [Doniyor-Nishonov](https://github.com/doniyor-nishonov)

---

Thank you for using Food Express! We hope you enjoy the platform and find it useful.
