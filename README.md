<h1> Online pharmacy store</h1>

<h2>Технологии</h2>

<ul class="discharged">
    <li>JDK 1.8+</li>
    <li>сервлет: Spring MVC 5, Thymeleaf</li>
    <li>авторизация пользователей: Spring Security</li>
    <li>доступ к данным: Hibernate, Spring Data JPA</li>
    <li>веб-служба в стиле REST в процессе разработки </li>
    <li>тесты: Spring Test, JUnit, Mockito</li>
    <li>веб-интерфейс: jQuery, jQuery Validate Plugin, Twitter Bootstrap</li>
    <li>база данных: MySQL</li>
    <li>контейнер сервлетов: Apache Tomcat 9</li>
    <li>сборка и деплой проекта: Maven + Webapp Runner plagin from Heroku </li>
</ul>

<h2>Функционал магазина</h2>

<ol class="discharged">
    <li>Наглядное представление ассортимента товаров</li>
    <li>Корзина покупателя
        <ul>
            <li>выбор товаров: добавление, удаление, изменение количества</li>
            <li>просмотр содержимого корзины</li>
            <li>оформление заказа</li>
            <li>хранение корзины покупателя в сессии и куки</li>
        </ul>
    </li>
    <li>Панель управления магазином
        <ul>
            <li>просмотр информации о заказах, редактирование </li>
            <li>поиск товаров по имени</li>
            <li>товары и категории: добавление, редактирование, удаление</li>
            <li>изменение статусов заказа</li>
            <li>поиск заказов</li>
        </ul>        
    </li>
    <li>Безопасный доступ к приложению
        <ul>
            <li>регистрация и авторизация пользователей</li>
            <li>ограничение доступа к панели управления</li>
        </ul>
    </li>
    <li>Двойная проверка содержимого форм: на стороне клиента и на стороне сервера</li>
</ol>

<h2>Валидация форм</h2>

<p>Проверка данных всех форм пользовательского и административного интерфейса выполняется
    дважды: на стороне пользователя и на стороне сервера.</p>
<ul class="discharged">
    <li>Проверка на стороне пользователя осуществляется с использованием jQuery Validate Plugin,
        который проверяет данные в момент ввода средствами JavaSript. Для посимвольной проверки строки применены
        регулярные выражения (regex). Визуализация дополнена классами Bootstrap.</li>
    <li>Проверка на стороне сервера выполняется с использованием пакетов <code>javax.validation</code> и
        <code>org.springframework.validation</code>.</li>
</ul>
<p>Такой подход к валидации форм делает процесс проверки данных комфортным
    для пользователя и вместе с тем гарантирует выполнение проверки при отключённом
    JavaScript в браузере пользователя.</p>

<h2>Обработка исключений</h2>

<p>В приложении реализована централизованная обработка исключений классом
    <code>sha.store.exception.ExceptionController</code> с аннотацией
    <code>@ControllerAdvice</code>, предоставленной Spring.</p>

<h2>Модель базы данных</h2>

<p>База данных приложения состоит из 11 связанных таблиц, отображаемых средствами Hibernate в 11 классов Entity.</p>

<br>
<p>Слой доступа к данным на первоначальном этапе разработки был представлен классами DAO,
    а с введением функций разбивки на страницы и сортировки реализован
    с помощью репозитория Spring Data JPA.</p>

<h2>Пользовательские классы Spring</h2>

<p>Функционал фреймворков Spring MVC и Spring Security расширен следующими классами:</p>
<ul class="discharged">
    <li><code>UserDetailsServiceImpl</code> реализует интерфейс <code>UserDetailsService</code>
        и обеспечивает извлечение профиля пользователя из базы данных;</li>
    <li><code>ContextOnListener</code> реализует интерфейс  <code>ApplicationListener<ContextRefreshedEvent></code>
        при инициализации контектста приложения запрашивает в базе данные о категории товаров и производителей записывая 
        их в переменную ServletContext обеспечивая быструю загрузку и уменьшение количества обращений к базе</li>
    <li><code>ExceptionController</code> осуществляет централизованную обработку исключений;</li>
    <li><code>SessionCartInterceptor</code> реализует интерфейс <code>HandlerInterceptorAdapter</code>
        и проверяет до обработки запроса контроллерами, существует ли в модели атрибут корзины покупателя;
        при отсутствии создатся новая корзина; такое решение позволяет централизовать создание корзины,
        в том числе для случая, когда браузер пользователя не принимает cookies и поэтому
        не поддерживает хранение параметров сессии;</li>
</ul>

<h3>How to build</h3>
<p>Deploying Tomcat-based Java Web Applications with Webapp Runner.</p>
<p>The following tools are required:</p>

#####  - JDK 1.8 or later
#####  - Apache Maven

<p>To build your application simply run:</p>

##### $ mvn package
 
<p>And then run your app using the java command:</p>

##### $ java -jar target/dependency/webapp-runner.jar target/*.war
  
<p>That’s it. Your application should start up on port 8080.</p>
