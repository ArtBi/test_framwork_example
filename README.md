# Шаблон проекту для тестування веб-додатку

Цей проект є шаблоном для тестування веб-додатку книжкового магазину. Він включає в себе як API, так і UI тести, а також
необхідну інфраструктуру для їх виконання.

## Технічний стек

- Java 21
- Gradle 8.5
- TestNG 7.9.0
- REST Assured 5.4.0
- Playwright 1.42.0
- Allure 2.25.0
- Log4j2 2.22.1

## Структура проекту

```
.
├── apps/
│   ├── api/                 # Spring Boot додаток (бекенд)
│   └── web/                 # Веб-інтерфейс
├── automation/
│   ├── api-tests/          # API тести
│   │   ├── src/
│   │   │   ├── main/
│   │   │   └── test/
│   │   │       ├── java/
│   │   │       │   └── com/
│   │   │       │       └── bookstore/
│   │   │       │           └── api/
│   │   │       │               └── tests/
│   │   │       │                   ├── auth/
│   │   │       │                   ├── catalog/
│   │   │       │                   ├── cart/
│   │   │       │                   ├── order/
│   │   │       │                   └── profile/
│   │   │       └── resources/
│   │   │           ├── testng.xml
│   │   │           └── log4j2.xml
│   └── web-tests/          # UI тести
│       ├── src/
│       │   ├── main/
│       │   └── test/
│       │       ├── java/
│       │       │   └── com/
│       │       │       └── bookstore/
│       │       │           └── web/
│       │       │               └── tests/
│       │       │                   ├── auth/
│       │       │                   ├── catalog/
│       │       │                   ├── cart/
│       │       │                   ├── order/
│       │       │                   └── profile/
│       │       └── resources/
│       │           ├── testng.xml
│       │           └── log4j2.xml
└── common-module/          # Спільний модуль
```

## Вимоги

- Java 21 або вище
- Node.js 20.x або вище (для UI тестів)
- Docker та Docker Compose (для локального розгортання)
- PostgreSQL 15 або вище

## Налаштування середовища

1. Клонуйте репозиторій:
   ```bash
   git clone https://github.com/your-username/web-api-template.git
   cd web-api-template
   ```

2. Запустіть локальне середовище за допомогою Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. Перевірте, що всі сервіси запущені:
   ```bash
   docker-compose ps
   ```

## Запуск тестів

### API тести

Для запуску API тестів:

```bash
./gradlew :automation:api-tests:test
```

Додаткові параметри:

- `-Dapi.base.url=http://your-api-url` - URL API
- `-Dapi.username=your-username` - ім'я користувача
- `-Dapi.password=your-password` - пароль

### UI тести

Для запуску UI тестів:

```bash
./gradlew :automation:web-tests:test
```

Додаткові параметри:

- `-Dweb.base.url=http://your-web-url` - URL веб-додатку
- `-Dbrowser=chromium|firefox|webkit` - браузер для тестів
- `-Dheadless=false` - запуск браузера у видимому режимі

### Генерація звітів Allure

Після виконання тестів, згенеруйте звіт Allure:

```bash
./gradlew allureReport
./gradlew allureServe
```

## Структура тестів

### API тести

- `BaseApiTest` - базовий клас для API тестів
- Тестові класи організовані за функціональними модулями:
    - `auth/` - тести автентифікації
    - `catalog/` - тести каталогу книг
    - `cart/` - тести кошика
    - `order/` - тести замовлень
    - `profile/` - тести профілю користувача

### UI тести

- `BaseUITest` - базовий клас для UI тестів
- Тестові класи організовані за функціональними модулями:
    - `auth/` - тести інтерфейсу автентифікації
    - `catalog/` - тести інтерфейсу каталогу
    - `cart/` - тести інтерфейсу кошика
    - `order/` - тести інтерфейсу замовлень
    - `profile/` - тести інтерфейсу профілю

## Логування

- Логи зберігаються в директорії `logs/`
- Для API тестів:
    - `api-tests.log` - загальний лог
    - `api-tests-error.log` - лог помилок
- Для UI тестів:
    - `web-tests.log` - загальний лог
    - `web-tests-error.log` - лог помилок
    - `playwright.log` - лог Playwright

## Безпека

- Всі конфігураційні дані зберігаються в змінних середовища
- Чутливі дані (паролі, токени) не зберігаються в репозиторії
- Використовується JWT для автентифікації
- Всі API endpoints захищені
- Реалізована валідація вхідних даних

## CI/CD

Проект готовий до інтеграції з CI/CD системами. Приклади конфігурацій:

- GitHub Actions
- Jenkins
- GitLab CI

## Додаткова документація

- [API документація](docs/api.md)
- [Тестова документація](docs/testing.md)
- [Архітектура](docs/architecture.md)
- [Безпека](docs/security.md)

## Ліцензія

Цей проект розповсюджується під ліцензією MIT. Див. файл [LICENSE](LICENSE) для деталей. 