<img src="https://capsule-render.vercel.app/api?type=waving&color=BDBDC8&height=150&section=header" />

# 🛒 [땅땅땅] 우리동네 경매서비스

> 땅땅땅 서비스에서 지명의 DB화, 이웃 동네 찾는 로직이 있는 프로젝트입니다.

<img width="900" alt="제목을-입력해주세요_-001 (1)" src="https://github.com/IP-I-s-Protocol/DDang/assets/151606621/575a46f1-8128-44fd-ad01-9c158fe15148">

---
## 👫🏼 Team
|<img src="https://avatars.githubusercontent.com/u/151606621?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/97017924?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/148612321?v=4" width="150" height="150"/>|<img src="https://avatars.githubusercontent.com/u/120919984?v=4" width="150" height="150"/>|
|:-:|:-:|:-----------------------------------------------------------------------------------------:|:-:|
|[@beginninggrace](https://github.com/beginninggrace)<br/>|[@kimpangya](https://github.com/kimpangya)|           구동현<br/>[@kudongku](https://github.com/kudongku)                      |boy who loves potato<br/>[@potatobboi](https://github.com/potatobboi)|

---
## 🍀 ERD

![img.png](img.png)

## 프로젝트 생성
### 의존성 생성하기
```java
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'com.mysql:mysql-connector-j'
    ...

    //POI
    implementation 'org.apache.poi:poi:5.2.3'
    implementation group: 'org.apache.poi', name: 'poi-ooxml', version: '5.2.4'
}
```
---
## 엑셀 파일 준비
![](https://velog.velcdn.com/images/kudongku/post/dddfc9b8-81ba-4fbc-90dc-c6a72242bf2e/image.png)
이러한 엑셀 파일을 src/main/resources에 준비해줍니다.

---

## Entity와 Repository
```java
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin_district")
public class AdminDistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String state;

    @Column
    private String county;

    @Column
    private String city;

    @Column
    private String district;

    @Column
    private String village;

    @Column
    private Double x;

    @Column
    private Double y;

    public AdminDistrict(
        String state,
        String county,
        String city,
        String district,
        String village,
        Double x,
        Double y
    ) {
        this.state = state;
        this.county = county;
        this.city = city;
        this.district = district;
        this.village = village;
        this.x = x;
        this.y = y;
    }

}
```
AdminDistrict(행정구역)이라는 Entity를 생성했습니다.

Column들의 이름은 미국 행정구역에서 따왔는데요,
아무래도 한국 행정구역의 이름을 따오면 Do, Si이런식이라 예약어에 걸리기도 하고, 직관성을 가져오기 위해서 미국 행정구역을 따왔습니다.

위도와 경도는 x,y로 결정합니다.

---

## Service 생성
```java
@Slf4j(topic = "AdminDistrictService")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminDistrictService {

    private final static String excelDirectory = "src/main/resources/AdminDistrictExcel.xlsx";

    private final AdminDistrictRepository adminDistrictRepository;

    @Transactional
    public void createAdminDistrict() {

        try (FileInputStream file = new FileInputStream(excelDirectory)) {

            List<AdminDistrict> townList = new ArrayList<>();
            Workbook workbook = WorkbookFactory.create(file);
            int sheets = workbook.getNumberOfSheets();

            for (int i = 0; i < sheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);

                for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
                    Row row = sheet.getRow(j);

                    townList.add(new AdminDistrict(
                        row.getCell(0).getStringCellValue(),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(3).getStringCellValue(),
                        row.getCell(4).getStringCellValue(),
                        row.getCell(5).getNumericCellValue(),
                        row.getCell(6).getNumericCellValue()
                    ));
                }

            }

            adminDistrictRepository.saveAll(townList);

        } catch (IOException | EncryptedDocumentException e) {
            log.error(e.getMessage());
        }

    }

}
```
Apache POI를 통해 엑셀 파일을 읽어오는 과정입니다.

### FileInputStream file = new FileInputStream(excelDirectory)
엑셀 파일을 디렉토리주소를 통해 가져오는 과정입니다.

### Workbook workbook = WorkbookFactory.create(file)
가져온 file을 WorkbookFactory를 통해 workbook으로 생성합니다.

### Sheet sheet = workbook.getSheetAt(i);
제가 준비한 엑셀파일에는 sheet로 경기도, 강원도, 경상남도,,, 식으로 분리가 되어있어서 일단 sheet별로 순회합니다.

### Row row = sheet.getRow(j);
sheet안에 있는 Row를 순회하면서 값들을 통해 엔티티를 생성해줍니다. 생성한 엔티티를 db에 넣어줍니다.

---

이런 메소드를 실행시켜주기 위해서 테스트 코드가 필요합니다.

```java
@SpringBootTest
class CityDbDdangApplicationTests {

    @Autowired
    AdminDistrictService adminDistrictService;

    @Test
    @Rollback(false)
    @DisplayName("townList 엑셀 파일 DB에 저장")
    void saveTownListDB() {
        //given, when,then
        adminDistrictService.createAdminDistrict();
    }

}
```
DB에 저장하기 위한 용도이니, 
Rollback을 false로 지정해줍니다.

---
## 테스트 코드 실행하기
![](https://velog.velcdn.com/images/kudongku/post/4d6e1cbe-f134-444e-a615-250aa27082b6/image.png)
정상적으로 실행이 잘 되네요!
![](https://velog.velcdn.com/images/kudongku/post/92647007-de54-4dd7-9376-5b81c35d9de4/image.png)
DB에도 값이 잘 들어간 것을 볼 수 있습니다.

<img src="https://capsule-render.vercel.app/api?type=waving&color=BDBDC8&height=150&section=footer" />
