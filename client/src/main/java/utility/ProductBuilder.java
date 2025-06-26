package utility;

import data.*;
import exceptions.EndInputException;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.IntPredicate;

public class ProductBuilder {
    private final Scanner scanner = new Scanner(System.in);

    public ProductBuilder() {
    }

    public Product builProduct() throws EndInputException {
        String name = askNonEmptyString("Введите название товара: ");
        Coordinates coordinates = buildCoordinates();
        Float price = askNullPositiveFloat("Введите цену: ");
        UnitOfMeasure unitOfMeasure = askEnum(UnitOfMeasure.class, "введите единицу измерения: ");
        Organization organization = buildOrganization();
        return new Product(null, name, coordinates, price, unitOfMeasure, organization, null);
    }

    private Coordinates buildCoordinates() throws EndInputException {
        int x = askInt("Введите координату x(<=931)", value -> value <= 931);
        Long y = askNonNullLong("Введите координату y: ");
        return new Coordinates(x, y);
    }

    private Organization buildOrganization() throws EndInputException {
        System.out.print("Добавить организацию-производителя?(y/n): ");
        if (!safeNextLine().equalsIgnoreCase("y")) return null;

        String name = askNonEmptyString("Введите название организации: ");
        String fullName = askNonEmptyString("Введите полное название организации: ");
        OrganizationType type = askEnum(OrganizationType.class, "Введите тип организации(или оставьте пустым: ");

        return new Organization(name, fullName, type, null);
    }


    private String safeNextLine() throws EndInputException {
        try {
            return scanner.nextLine().trim();
        } catch (NoSuchElementException e) {
            throw new EndInputException();
        }
    }

    private String askNonEmptyString(String text) throws EndInputException {
        while (true) {
            System.out.println(text);
            String input = safeNextLine();
            if (!input.isEmpty()) return input;
            System.out.println("Поле не может быть пустым");
        }
    }

    private int askInt(String text, IntPredicate validator) throws EndInputException {
        while (true) {
            System.out.println(text);
            try {
                int input = Integer.parseInt(safeNextLine());
                if (validator.test(input)) return input;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите целое число");
            }
        }
    }

    private Long askNonNullLong(String text) throws EndInputException {
        while (true) {
            System.out.println(text);
            try {
                return Long.parseLong(safeNextLine());
            } catch (NumberFormatException e) {
                System.out.println("Поле не может быть пустым и является целым числом");
            }
        }
    }

    private Float askNullPositiveFloat(String text) throws EndInputException {
        System.out.println(text);
        String input = safeNextLine();
        try {
            float val = Float.parseFloat(input);
            return (val > 0) ? val : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private <E extends Enum<E>> E askEnum(Class<E> enumClass, String text) throws EndInputException {
        while (true) {
            System.out.print(text + " ");
            for (E constant : enumClass.getEnumConstants()) System.out.println(constant);
            String input = safeNextLine();
            if (input.isEmpty()) return null;
            try {
                return Enum.valueOf(enumClass, input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректное значение");
            }
        }
    }


}

