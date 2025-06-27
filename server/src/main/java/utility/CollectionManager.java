package utility;

import data.Organization;
import data.Product;
import data.Products;

import java.time.ZonedDateTime;
import java.util.*;

public class CollectionManager {
    private final List<Product> products = new LinkedList<>();
    private final FileManager fileManager;
    private ZonedDateTime initTime;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        loadFromFile();
    }

    public List<Product> getProducts() {
        return products;
    }

    public ZonedDateTime getInitTime(){
        return initTime;
    }

    public String getCollectionType(){
        return products.getClass().getSimpleName();
    }

    public void add(Product product){
        products.add(product);
    }

    public void clear(){
        IdManager.clear();
        products.clear();
    }

    public void save() {
        Products container = new Products();
        container.setProducts(products);
        fileManager.loadToFile(container);
    }

    private void loadFromFile() {
        Products container = fileManager.readXml();
        if (container != null && container.getProducts() != null) {
            products.addAll(container.getProducts());
            for (Product p : products) {
                IdManager.registerProductId(p.getId()); // регистрируем ID
                if (p.getManufacturer() != null) {
                    IdManager.registerOrgId(p.getManufacturer().getId());
                }
            }
            validateCollection();
            initTime = ZonedDateTime.now();
            System.out.println("Коллекция загружена");
        } else {
            System.out.println("Файл пустой или повреждён.");
        }
    }

    private void validateCollection() {
        List<String> validationErrors = new ArrayList<>();
        Set<Integer> seenProductIds = new HashSet<>();
        Set<Integer> seenOrgIds = new HashSet<>();

        for (Product product : products) {
            List<String> productErrors = new ArrayList<>();
            Integer id = product.getId();
            if (id == null || id <= 0) {
                productErrors.add("ID должен быть положительным и не null");
            }else if (!seenProductIds.add(id)) {
                productErrors.add("ID продукта " + id + " не уникален");
            }
            try {
                product.setName(product.getName());
            } catch (Exception e) {
                productErrors.add("Название: " + e.getMessage());
            }
            try {
                product.setPrice(product.getPrice());
            } catch (Exception e) {
                productErrors.add("Цена: " + e.getMessage());
            }
            try {
                if (product.getCoordinates() == null) {
                    productErrors.add("Координаты не могут быть null");
                } else {
                    product.getCoordinates().validate();
                }
            } catch (Exception e) {
                productErrors.add("Координаты: " + e.getMessage());
            }
            try {
                Organization org = product.getManufacturer();
                if (org != null) {
                    if (org.getId() != null) {
                        if (!seenOrgIds.add(org.getId())) {
                            productErrors.add("ID организации " + org.getId() + " не уникален");
                        }
                    }
                    org.validate();
                }
            } catch (Exception e) {
                productErrors.add("Организация: " + e.getMessage());
            }
            // Добавляем ошибки по продукту в общий список
            if (!productErrors.isEmpty()) {
                validationErrors.add("Ошибки в продукте с ID " + id + ":\n - " + String.join("\n - ", productErrors));
            }
        }
        if (!validationErrors.isEmpty()) {
            System.err.println("Обнаружены ошибки при валидации:\n");
            for (String err : validationErrors) {
                System.out.println(err);
                System.out.println();
            }
            System.exit(1);
        } else {
            System.out.println("Валидация успешно пройдена!");
        }
    }

}
