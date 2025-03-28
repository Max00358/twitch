![Image](https://github.com/user-attachments/assets/796c83d4-17a0-47b4-a229-907892ed3722)
## **Twitch++: Personalized Twitch Resources Recommendation Engine**  
### **Live Demo**
 **Check out the project live ✨[HERE](https://ipp8hdhxi4.us-east-2.awsapprunner.com/)✨** 

### **Project Overview**  
Twitch++ is a full-stack web application designed to help users search, explore, and get personalized recommendations for Twitch resources (streams, videos, and clips). Built with modern technologies and deployed on AWS, Twitch++ delivers a seamless and engaging user experience.  

### **Key Features**  
✅ **Search & Explore Twitch Resources:**  
   - Search for Twitch streams, videos, and clips in real-time.  
   - Retrieve data using the Twitch API for accurate and up-to-date results.  

✅ **Personalized Recommendations:**  
   - Content-based recommendation engine suggests resources based on your favorite collections.  
   - Extracts game information from Twitch resources to tailor recommendations.  

✅ **User Authentication:**  
   - Secure register/login/logout functionality powered by **Spring Security**.  

✅ **Rich & User-Friendly Interface:**  
   - Built with **React** and **Ant Design** for a modern, responsive, and intuitive UI.  

✅ **Efficient Backend Operations:**  
   - RESTful APIs implemented with **Spring Boot**.  
   - CRUD operations managed using **Spring Data JDBC** with a **MySQL database** hosted on **AWS RDS**.  

✅ **Optimized Performance:**  
   - **Spring Boot Caching** with **Caffeine** for faster response times.  

✅ **Cloud Deployment:**  
   - Containerized application deployed to **AWS App Runner** for scalability and reliability.  
---
### **Tech Stack**  
### **Frontend**  
- **React**  
- **Ant Design**  

### **Backend**  
- **Spring Boot**  
- **Spring Security**  
- **Spring Data JDBC**  
- **OpenFeign** (Twitch API integration)  

### **Database**  
- **MySQL** (Hosted on AWS RDS)  

### **Caching**  
- **Caffeine**  

### **Deployment**  
- **Docker**  
- **AWS App Runner**  

## **How It Works**  
1. **Search Twitch Resources:**  
   - Users can search for Twitch streams, videos, and clips using the intuitive search interface.  
2. **Personalized Recommendations:**  
   - The system analyzes your favorite collections and recommends similar resources based on game information.  
3. **User Authentication:**  
   - Secure login and registration ensure a personalized experience.  
4. **Efficient Backend:**  
   - RESTful APIs handle requests, and Spring Boot Caching ensures optimal performance.  
5. **Cloud Deployment:**  
   - The application is containerized and deployed on AWS App Runner for scalability and reliability.  

## **Getting Started**  
### **Prerequisites**  
- Java 17+  
- Node.js 16+  
- MySQL 8+  
- Docker (optional)  

### **Installation**  
1. Clone the repository:  
   ```bash  
   git clone https://github.com/your-username/twitch-plus-plus.git  
   ```  
2. Set up the backend:  
   - Configure the `application.properties` file with your database and Twitch API credentials.  
   - Run the Spring Boot application:  
     ```bash  
     ./mvnw spring-boot:run  
     ```  
3. Set up the frontend:  
   - Navigate to the `frontend` directory and install dependencies:  
     ```bash  
     npm install  
     ```  
   - Start the React app:  
     ```bash  
     npm start  
     ```  
4. Access the application at `http://localhost:3000`.  

---
## **Contributing**  
Contributions are welcome! If you'd like to contribute, please:  
1. Fork the repository.  
2. Create a new branch for your feature/bugfix.  
3. Submit a pull request.  

## **License**  
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.  

### **Why Twitch++?**  
Twitch++ is more than just a recommendation engine—it’s a gateway to discovering your next favorite Twitch content. Whether you’re a casual viewer or a hardcore gamer, Twitch++ ensures you never miss out on the action.  
