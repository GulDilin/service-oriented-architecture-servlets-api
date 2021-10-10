# Service Oriented Architecture. Laboratory Work 1

### Variant 3005
Разработать веб-сервис на базе сервлета, реализующий управление коллекцией объектов, и клиентское веб-приложение,
предоставляющее интерфейс к разработанному веб-сервису. В коллекции необходимо хранить объекты класса City,
описание которого приведено ниже:

```java
public class City {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer area; //Значение поля должно быть больше 0, Поле не может быть null
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    private float metersAboveSeaLevel;
    private int populationDensity; //Значение поля должно быть больше 0
    private Integer carCode; //Значение поля должно быть больше 0, Максимальное значение поля: 1000, Поле может быть null
    private Climate climate; //Поле не может быть null
    private Human governor; //Поле может быть null
}
public class Coordinates {
    private long x; //Значение поля должно быть больше -667
    private int y;
}
public class Human {
    private java.time.LocalDate birthday;
}
public enum Climate {
    RAIN_FOREST,
    MONSOON,
    SUBARCTIC
}
```

### Веб-сервис должен удовлетворять следующим требованиям:
- API, реализуемый сервисом, должен соответствовать рекомендациям подхода RESTful.
- Необходимо реализовать следующий базовый набор операций с объектами коллекции: добавление нового элемента, получение элемента по ИД, обновление элемента, удаление элемента, получение массива элементов.
- Операция, выполняемая над объектом коллекции, должна определяться методом HTTP-запроса.
- Операция получения массива элементов должна поддерживать возможность сортировки и фильтрации по любой комбинации полей класса, а также возможность постраничного вывода результатов выборки с указанием размера и порядкового номера выводимой страницы.
- Все параметры, необходимые для выполнения операции, должны передаваться в URL запроса.
- Данные коллекции, которыми управляет веб-сервис, должны храниться в реляционной базе данных.
- Информация об объектах коллекции должна передаваться в формате json.
- В случае передачи сервису данных, нарушающих заданные на уровне класса ограничения целостности, сервис должен возвращать код ответа http, соответствующий произошедшей ошибке.
- Веб-сервис должен быть "упакован" в веб-приложение, которое необходимо развернуть на сервере приложений Tomcat.

### Помимо базового набора, веб-сервис должен поддерживать следующие операции над объектами коллекции:
- Вернуть количество объектов, значение поля climate которых больше заданного.
- Вернуть массив объектов, значение поля name которых содержит заданную подстроку.
- Вернуть массив объектов, значение поля name которых начинается с заданной подстроки.

### Эти операции должны размещаться на отдельных URL.

### Требования к клиентскому приложению:

- Клиентское приложение может быть написано на любом веб-фреймворке, который можно запустить на сервере helios.
- Клиентское приложение должно обеспечить полный набор возможностей по управлению объектами коллекции, предоставляемых веб-сервисом -- включая сортировку, фильтрацию и постраничный вывод.
- Клиентское приложение должно преобразовывать передаваемые сервисом данные в человеко-читаемый вид -- параграф текста, таблицу и т.д.
- Клиентское приложение должно информировать пользователя об ошибках, возникающих на стороне сервиса, в частности, о том, что сервису были отправлены невалиданые данные.

### Веб-сервис и клиентское приложение должны быть развёрнуты на сервере helios.

### Вопросы к защите лабораторной работы:

- Подходы к проектированию приложений. "Монолитная" и сервис-ориентированная архитектура.
- Понятие сервиса. Общие свойства сервисов.
- Основные принципы SOA. Подходы к реализации SOA, стандарты и протоколы.
- Общие принципы построения и элементы сервис-ориентированных систем.
- Понятие веб-сервиса. Определение, особенности, отличия от веб-приложений.
- Категоризация веб-сервисов. RESTful и SOAP. Сходства и отличия, области применения.
- RESTful веб-сервисы. Особенности подхода. Понятия ресурса, URI и полезной нагрузки (payload).
- Виды RESTful-сервисов. Интерпретация методов HTTP в RESTful.
- Правила именования ресурсов в RESTful сервисах.
- Разработка RESTful сервисов. Языки программирования, фреймворки и библиотеки.

