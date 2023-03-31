package com.example.organaizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class OrganaizerApplication implements CommandLineRunner {
    private final XmlUserRepository userRepository;

    @Autowired
    public OrganaizerApplication(XmlUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrganaizerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите команду (help для справки):");
            String command = scanner.nextLine();
            switch (command) {
                case "help":
                    printHelp();
                    break;
                case "add":
                    addUser(scanner);
                    break;
                case "edit":
                    editUser(scanner);
                    break;
                case "delete":
                    deleteUser(scanner);
                    break;
                case "list":
                    printUsers();
                    break;
                case "find":
                    findUser(scanner);
                    break;
                case "exit":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    private void printHelp() {
        System.out.println("help - показать список команд");
        System.out.println("add - добавить пользователя");
        System.out.println("edit - редактировать пользователя");
        System.out.println("delete - удалить пользователя");
        System.out.println("list - показать список пользователей");
        System.out.println("find - найти пользователя");
        System.out.println("exit - выйти из приложения");
    }

    private void addUser(Scanner scanner) {
        User user = new User();
        System.out.println("Введите ФИО:");
        user.setFullName(scanner.nextLine());
        System.out.println("Введите должность:");
        user.setPosition(scanner.nextLine());
        System.out.println("Введите организацию:");
        user.setOrganization(scanner.nextLine());
        System.out.println("Введите почтовый адрес:");
        user.setEmail(scanner.nextLine());
        List<String> phones = new ArrayList<>();
        while (true) {
            System.out.println("Введите номер телефона или нажмите Enter для завершения:");
            String phone = scanner.nextLine();
            if (phone.isEmpty()) {
                break;
            }
            phones.add(phone);
        }
        user.setPhones(phones);
        userRepository.save(user);
        System.out.println("Пользователь добавлен.");
    }

    private void editUser(Scanner scanner) {
        System.out.println("Введите id пользователя, которого нужно отредактировать:");
        int id = Integer.parseInt(scanner.nextLine());
        User user = userRepository.findById(id);
        if (user == null) {
            System.out.println("Пользователь не найден.");
            return;
        }
        System.out.println("Введите новое ФИО:");
        user.setFullName(scanner.nextLine());
        System.out.println("Введите новую должность:");
        user.setPosition(scanner.nextLine());
        System.out.println("Введите новую организацию:");
        user.setOrganization(scanner.nextLine());
        System.out.println("Введите новый почтовый адрес:");
        user.setEmail(scanner.nextLine());
        List<String> phones = new ArrayList<>();
        while (true) {
            System.out.println("Введите новый номер телефона или нажмите Enter для завершения:");
            String phone = scanner.nextLine();
            if (phone.isEmpty()) {
                break;
            }
            phones.add(phone);
        }
        user.setPhones(phones);
        userRepository.update(user);
        System.out.println("Пользователь отредактирован.");
    }

    private void deleteUser(Scanner scanner) {
        System.out.println("Введите id пользователя, которого нужно удалить:");
        int id = Integer.parseInt(scanner.nextLine());
        User user = userRepository.findById(id);
        if (user == null) {
            System.out.println("Пользователь не найден.");
            return;
        }
        userRepository.delete(id);
        System.out.println("Пользователь удален.");
    }

    private void printUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            System.out.println("Список пользователей пуст.");
        } else {
            for (User user : users) {
                System.out.println(user.toString());
            }
        }
    }

    private void findUser(Scanner scanner) {
        System.out.println("Введите id пользователя:");
        int id = Integer.parseInt(scanner.nextLine());
        User user = userRepository.findById(id);
        if (user == null) {
            System.out.println("Пользователь не найден.");
        } else {
            System.out.println(user.toString());
        }
    }
}
