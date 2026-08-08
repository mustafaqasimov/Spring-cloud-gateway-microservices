Spring Cloud Gateway Microservices

API Gateway (Spring Cloud Gateway) ilə üç backend servisə (Auth, Profile, Feedback) yönləndirmə edən mikroservis layihəsi.

Arxitektura

Client sorğuları yalnız API Gateway-ə (8080 portu) gedir. Gateway isə bunları daxili şəbəkə üzərindən Auth Service-ə (8081), Profile Service-ə (8082) və Feedback Service-ə (8083) yönləndirir. Yalnız Gateway xaricə açıqdır, digər üç servis Docker şəbəkəsinin daxilindən başqa heç yerdən əlçatan deyil.

Təhlükəsizlik

JWT yalnız Gateway-də, bir dəfə yoxlanılır. Uğurlu olduqda, Gateway token-in özünü yox, X-User-Id və X-User-Role başlıqlarını backend servislərə ötürür.

X-Internal-Api-Key adlı paylaşılan bir sirr Gateway tərəfindən hər sorğuya əlavə olunur. Hər backend servis bu açarı yoxlayır və Gateway-i keçməyən sorğuları rədd edir.

Profile Service, sorğunu edən X-User-Id-nin profilin sahibi ilə uyğun olduğunu yoxlayır, yalnız sahib oxuma, yeniləmə və silmə edə bilir.

Rate limiting Redis əsasında işləyir: login və register endpoint-lərinə saniyədə 2 sorğu, digər endpoint-lərə saniyədə 20 sorğu icazə verilir.

Endpoint-lər

Auth Service: register və login.

Profile Service: profil yaratma, oxuma, yeniləmə və silmə — hamısı JWT tələb edir, yalnız sahibi əməliyyat edə bilər.

Feedback Service: feedback göndərmə və səhifələnmiş siyahı alma — JWT tələb edir.

Hər backend servisin öz Swagger UI-si var.

Texnologiyalar

Java 21, Spring Boot 4.1, Spring Cloud Gateway, Spring Data JPA və PostgreSQL (hər servisin öz verilənlər bazası), Flyway, Redis, java-jwt, MapStruct, test üçün JUnit 5 və Mockito.

İşə salma

.env faylını .env.example əsasında hazırlayıb DB_USERNAME, DB_PASSWORD, JWT_SECRET və INTERNAL_API_KEY dəyərlərini doldurmaq lazımdır. Sonra docker compose up --build ilə bütün sistem qaldırılır. JWT_SECRET və INTERNAL_API_KEY bütün servislərdə eyni olmalıdır.

Testlər

Hər modulda mvn clean test kifayətdir. Testlər Mockito ilə yazılıb, Spring context və ya verilənlər bazası tələb etmir.
