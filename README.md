# E-Learning (Spring Boot + Thymeleaf MVC) — Prototype

## 1) Chọn version “chuẩn”

- **OS**: Ubuntu/Linux (bạn đang dùng Linux)
- **JDK**: **Java 21 (LTS)** (khuyến nghị Temurin/Adoptium)
- **Framework**: **Spring Boot 3.4.x**
- **Build tool**: Maven (wrapper sẽ tự kéo đúng Maven)
- **DB**: **MySQL 8.0** (schema hiện tại dùng `AUTO_INCREMENT`, `ENUM`, triggers/views/procedure theo MySQL)
- **UI**: Thymeleaf + Bootstrap (CDN) cho prototype

## 2) Cài IDE + tooling (Linux)

### 2.1 Cài IntelliJ IDEA

Bạn có thể dùng:
- IntelliJ IDEA Community (đủ cho Spring Boot + Thymeleaf)
- IntelliJ IDEA Ultimate (tiện hơn cho DB tooling)

Khuyến nghị cài bằng Snap:

```bash
sudo snap install intellij-idea-community --classic
```

### 2.2 Cài Java 21 (Temurin)

```bash
sudo apt update
sudo apt install -y wget apt-transport-https ca-certificates

# Adoptium repo
wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | sudo gpg --dearmor -o /usr/share/keyrings/adoptium.gpg
echo "deb [signed-by=/usr/share/keyrings/adoptium.gpg] https://packages.adoptium.net/artifactory/deb $(. /etc/os-release && echo $VERSION_CODENAME) main" | sudo tee /etc/apt/sources.list.d/adoptium.list

sudo apt update
sudo apt install -y temurin-21-jdk
java -version
```

### 2.3 Cài Docker Engine + Compose

Nếu máy đã có docker/compose thì bỏ qua.

```bash
sudo apt update
sudo apt install -y docker.io docker-compose-plugin
sudo usermod -aG docker "$USER"
newgrp docker
docker version
docker compose version
```

## 3) Setup DB (MySQL) theo schema

### 3.1 Khởi chạy MySQL bằng Docker Compose

```bash
docker compose -f infra/docker-compose.yml up -d
```

Thông tin mặc định:
- MySQL host: `localhost`
- Port: `3306`
- DB: `elearning_db`
- User: `elearning`
- Pass: `elearning`
- Root pass: `root`

### 3.2 Kiểm tra DB

```bash
docker exec -it elearning-mysql mysql -uroot -proot -e "SHOW DATABASES;"
docker exec -it elearning-mysql mysql -uroot -proot -e "USE elearning_db; SHOW TABLES;"
```

## 4) Chạy app (prototype)

### 4.1 Run bằng Maven wrapper

```bash
./mvnw spring-boot:run
```

### 4.2 URL demo

- Trang chủ: `http://localhost:8080/`
- Đăng ký: `http://localhost:8080/register`
- Đăng nhập: `http://localhost:8080/login`
- Danh sách khóa học: `http://localhost:8080/courses`

Tài khoản seed (tự tạo khi app chạy lần đầu):
- admin / `admin123`
- student / `student123`
- teacher / `teacher123`

## 5) Ghi chú kiến trúc prototype

- MVC: Controller → Service → Repository → Entity (JPA) + Thymeleaf templates
- Auth: Prototype dùng **HttpSession** (đơn giản để demo luồng web).
  Khi vào phase “production”, nên chuyển sang Spring Security.

