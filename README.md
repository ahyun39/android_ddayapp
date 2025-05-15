# 📱 D-Day Application (Android)

<br>

## 🖥️ 프로젝트 개요

D-Day Application은 사용자가 중요한 일정과 기념일을 쉽게 관리할 수 있도록 돕는 안드로이드 애플리케이션입니다.
사용자는 새로운 일정을 추가하고, 남은 일수(D-Day)를 확인하며, D-Day 진행 상황을 시각적으로 파악할 수 있습니다.

- 프로젝트 기간 : 2025.03.01.(토)  ~ 2025.03.13.(목)


<br><br>


## 📝 주요 기능

    ✅ 일정 추가

      - 원하는 일정의 날짜와 이름을 입력하여 새로운 일정을 추가합니다.

    ✅ D-Day 표시

      - 현재 날짜 기준으로 일정까지 남은 일수를 "D-Day" 형식으로 표시합니다.
        
    ✅ 진행률 표시

      - ProgressBar를 활용하여 일정 진행률을 시각적으로 제공합니다.

    ✅ 일정 목록 제공

      - 추가된 일정을 RecyclerView를 통해 스크롤하며 확인할 수 있습니다.

    ✅ 위젯 기능

      - 사용자가 선택한 일정으로 위젯을 생성할 수 있습니다.

<br><br>


## 🚀 사용 방법  

<br>

### 📌 일정 추가  
1. 메인 화면에서 **`일정 추가`** 버튼을 눌러 새 일정을 추가합니다.  
2. 일정과 날짜를 선택한 후, **`SAVE`** 버튼을 눌러 저장합니다.  

<br>

### ✏️ 일정 수정  
1. 수정할 일정의 **`EDIT`** 버튼을 눌러 일정을 불러옵니다.  
2. 직접 일정과 날짜를 수정한 후, **`UPDATE`** 버튼을 눌러 저장합니다.  

<br>

### 🗑️ 일정 삭제  
- 삭제할 일정의 **`DELETE`** 버튼을 눌러 즉시 삭제합니다.  

<br>

### ⭐ 대표 일정 설정  

  > 기본 정렬 방식
  - 일정은 남은 기간이 가장 짧은 순서로 자동 정렬됩니다.  

  > 대표 일정 설정 방법
  1. 메인 화면에서 **`대표 일정 선택`** 버튼을 눌러 대표 일정으로 설정할 일정을 선택합니다.  
  2. 선택한 후, **`대표 일정으로 저장`** 버튼을 눌러 설정을 완료합니다.  
  3. 대표 일정은 기본 정렬 방식에서 가장 상단에 위치하여 표시됩니다.  

<br>

### 📲 위젯 설정  
1. 홈 화면에서 위젯을 생성합니다.  
2. 위젯에 표시할 일정을 선택한 후, **`위젯 생성`** 버튼을 누르면 해당 일정이 위젯에 표시됩니다.  



<br><br>


## 💻 필수 요구사항

| No.  | 구분               | 기능                                 | 구현 정도(⭐⭐⭐⭐⭐)                                            |
| ---- | ------------------ | ------------------------------------ | ----------------------------------------------------------- |
| 1 | 일정 목록 | 일정 추가 | ⭐⭐⭐⭐⭐ |
| 2 | | 대표 일정 설정 | ⭐⭐⭐⭐⭐ |
| 3 | | Recycler View 구현 | ⭐⭐⭐⭐⭐ |
| 4	| D-Day 표시 |	일정까지 남은 일수 계산 및 표시 |	⭐⭐⭐⭐⭐ |
| 5	| 진행률 표시 |	ProgressBar를 통한 진행률 시각화 | ⭐⭐⭐⭐⭐ |
| 6	| 위젯 기능 |	사용자가 선택한 일정으로 위젯 생성 | ⭐⭐⭐⭐⭐ |
| 7	| UI/UX 개선 |	직관적인 화면 구성 및 디자인 개선 |	⭐⭐⭐⭐ |


<br><br>


## 🛠 Tech Stack

- **개발 언어:** Kotlin, XML
- **개발 환경:** Android Studio
- **아키텍처 패턴:** MVVM (Model-View-ViewModel)

<br><br>



## 📂 프로젝트 구조

<p align="center">
  <img src="https://github.com/user-attachments/assets/3b0c198a-fb07-4cf1-94af-d4faa40368c8" width="80%" height="50%" alt=" ">
</p>


## ERD

```

┌───────────────────────────┐             ┌────────────────────────────┐
│         events            │             │      selected_events       │
├───────────────────────────┤             ├────────────────────────────┤
│ id (PK)                   │◄────────────┤ event_id (FK → events.id)  │
│ title                     │             │ id (PK)                    │
│ event_date                │             │ is_main_event              │
│ start_date                │             └────────────────────────────┘
│ is_main_event             │
└───────────────────────────┘

```

<br>

TABLE : `events`
- `id(PK)` : INT
- `title` : VARCHAR, NOT NULL, 이벤트 제목
- `event_date` : DATETIME,  NOT NULL, 이벤트 날짜
- `start_date` : DATETIME,  NOT NULL, 시작 날짜
- `is_main_event` : BOOLEAN, 대표 일정 여부

<br>

TABLE : `selected_events`
- `id(PK)` : INT
- `event_id(FK)` : INT
- `is_main_event` : BOOLEAN, 대표 일정 여부



<br><br>

## 🎥 실행 영상  

 **이미지 클릭 시 실행 영상으로 이동**  

<p align="center">
  <a href="https://youtube.com/shorts/Yisq9c2B4DM?feature=share">
    <img src="https://github.com/user-attachments/assets/360b56a2-fc59-4893-a410-bcbffdb0f39f" width="50%" height="40%" alt="실행 영상 보기">
  </a>
</p>

<br>

### 📲 갤럭시 S20 실행 화면  

<table>
  <tr>
    <th>아이콘 & 메인화면</th>
    <th>일정 목록 & 수정</th>
    <th>일정 삭제 & 대표 일정 설정</th>
  </tr>
  <tr>
    <td valign="top">
      <br>▶️ 디데이앱 아이콘<br><br>
      <img src="https://github.com/user-attachments/assets/de694bfe-e5bc-4767-839e-0c27e0cae9b7" width="200">
    </td>
    <td valign="top">
      <br>▶️ 일정 목록<br><br>
      <img src="https://github.com/user-attachments/assets/92346d0f-8599-4246-b22a-ccd9637ae48d" width="200">
    </td>
    <td valign="top">
      <br>▶️ 일정 삭제<br><br>
      <img src="https://github.com/user-attachments/assets/206c3ade-9e4f-49c0-822b-e43fc6fabb2f" width="200">
    </td>
  </tr>
  <tr>
    <td valign="top">
      <br>▶️ 메인 화면<br><br>
      <img src="https://github.com/user-attachments/assets/4ad41dbe-b36e-4fdf-bfd9-2003acfeb91a" width="200">
    </td>
    <td valign="top">
      <br>▶️ 일정 수정<br><br>
      <img src="https://github.com/user-attachments/assets/2dcbd218-7292-4dcc-8c92-ee86e2a5c81f" width="200">
    </td>
    <td valign="top">
      <br>▶️ 대표 일정 설정<br><br>
      <img src="https://github.com/user-attachments/assets/83cdb2d5-7aa3-4d44-a173-1189840a60b0" width="200">
    </td>
  </tr>
</table>

<br>

<table>
  <tr>
    <th>대표 일정 & 위젯 설정</th>
    <th>위젯 선택 & 화면 표시</th>
    <th>위젯 추가 & 크기 조절</th>
  </tr>
  <tr>
    <td valign="top">
      <br>▶️ 대표 일정 상단 위치<br><br>
      <img src="https://github.com/user-attachments/assets/3ad928d9-949e-4a15-aa05-4b4962bbad4d" width="200">
    </td>
    <td valign="top">
      <br>▶️ 위젯에 표시될 일정 선택<br><br>
      <img src="https://github.com/user-attachments/assets/76c3e94a-5c40-401d-bc0c-d2206477584a" width="200">
    </td>
    <td valign="top">
      <br>▶️ 위젯 여러 개 생성 가능<br><br>
      <img src="https://github.com/user-attachments/assets/0a91e951-b334-46fd-8968-c5dc0914a2ea" width="200">
    </td>
  </tr>
  <tr>
    <td valign="top">
      <br>▶️ 위젯 설정 가능<br><br>
      <img src="https://github.com/user-attachments/assets/d9fa8df0-30c7-47f5-a264-b594fea26df1" width="200">
    </td>
    <td valign="top">
      <br>▶️ 위젯 화면<br><br>
      <img src="https://github.com/user-attachments/assets/4642f095-3468-4a04-849c-930ff877f394" width="200">
    </td>
    <td valign="top">
      <br>▶️ 위젯 가로 사이즈 조절<br><br>
      <img src="https://github.com/user-attachments/assets/4ceb645e-1674-4457-ab88-ecb99af740b8" width="200">
    </td>
  </tr>
</table>


📌 **[앱 아이콘 이미지 출처](https://m.blog.naver.com/jobobo12/223115307453)**




<br><br>

## 📅개발일지

| No.  | Date     | Function   | ToDo                                                         | Done                                                         |
| ---- | -------- | ------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1 | 2025.02.28 | 일정 추가 | 일정 추가 버튼 생성 | 일정명, 날짜 선택 후 일정 생성 |
| 2 | | 일정 목록 | 기존 ListView를 RecylerView로 변경 | RecylerView 적용 |
| 3 | | 일정 표시 | 일정 진행률 직관적인 디자인으로 수정 | 일정까지 남은 기간을 percent로 계산해서 progressbar로 나타냄 |
| 4 | 2025.03.02 | 일정 삭제 | 일정 삭제 버튼 생성 | 삭제 기능 생성 |
| 5 | | 일정 표시 | progressbar 위에 아이콘 추가 | progressbar 진행률 표시에 따라 아이콘이 함께 위치하도록 |
| 6 | 2025.03.03 | 위젯 | 위젯 생성 | 어플 내 일정 표시 화면 그대로 위젯으로 생성 |
| 7 | 2025.03.04 | 일정 목록 | 디데이 숫자 내림차순 정렬 | 일정이 임박한 순서대로 일정이 자동 정렬 |
| 8 | 2025.03.06 | 대표 일정 | 대표 일정 선택 | 선택한 대표 일정이 목록 상단에 위치하도록 설정(boolean is_main 사용) |
| 9 | | | | DB에서 일정이 생성될 때 is_main이라는 boolean 값을 column으로 갖도록 schema 수정 |
| 10 | | | 대표 일정 위젯 화면 | 위젯을 생성하면 설정한 대표 일정이 나타나도록 설정 |
| 11 | 2025.03.08 | 위젯 | 위젯 화면에 아이콘 추가 | progressbar 끝 지점에 아이콘이 위치하도록 |
| 12 | 2025.03.09 | 테스트 | 실제 폰에서 APK로 설치하여 테스트(1차) | 테스트 후 개선점 발견 |
| 13 | 2025.03.10 | 일정 수정 | 일정 수정 버튼 생성 | 일정명, 날짜 수정 후 일정 저장 |
| 14 | | 일정 표시 | 일정명이 너무 길어지는 경우 처리 | 텍스트가 정해진 범위 이상으로 설정되면 나머지는 ... 으로 표시되도록 수정 |
| 15 | | | | ...으로 표시된 일정은 일정명을 누르면 전체 텍스트 확인 가능하도록 |
| 16 | 2025.03.12 | 위젯 | 일정 선택 목록 생성 | 위젯 생성할 때 보여질 일정 선택 목록을 spinner로 생성 |
| 17 |  |  | 위젯 생성 시 사용자가 일정 선택 | 위젯 생성할 때 사용자가 선택한 일정이 보여지도록 생성 |
| 18 | 2025.03.13 | 테스트 | 실제 폰에서 APK로 설치하여 테스트(2차) | 테스트 후 개선점 발견 |
| 19 | | 캐시 | 앱 재설치 시 캐시 삭제 | 재설치 시 기존 캐시가 남아있는 문제 해결 |
| 20 | 2025.03.27 | 일정 추가 | 시작 날짜 설정 기능 추가 | 사용자가 이벤트의 시작 지점을 정할 수 있도록 시작 날짜 설정 기능 추가 |
| 21 | | 일정 표시 | 진행률 계산 개선 | 시작 날짜로부터의 진행률을 표시할 수 있도록 개선 |
| 22 | | 일정 수정 | 시작 날짜 수정 | 일정의 시작 날짜를 수정할 수 있도록 |
| 23 | | 위젯 | 진행률 계산 개선 | 시작 날짜로부터의 진행률을 표시하여 위젯을 통해 직관적으로 진행률 확인 가능 |




<br><br>



## 🔥이슈 관리

| No.   | Content                                                      | Solve    | follow-up                                                    |
| ---- | ------------------------------------------------------------ | -------- | ------------------------------------------------------------ |
| 1 | D-100 이하일 때만 progressbar가 채워짐 | ✅ | percent 계산 방식 개선 필요 (시작 날짜를 사용자가 설정할 수 있도록) |
| 2 | 달리는 아이콘이 percent에 따라 색 안쪽/바깥쪽에 위치하는 문제 | ✅ | progressbar에서 채워진 부분 끝에 아이콘이 위치하도록 X_transition 계산 수정 |
| 3 | 대표 일정 설정 안됨 | ✅ | DatabaseHelper와 EventSelectionActivity에서 주고받는 변수 값 확인 후 코드 개선 |
| 4 | 대표 일정 위젯 설정 안됨 | ✅ | DatabaseHelper와 DDayWidgetProvider에서 주고받는 변수 값 확인 후 코드 개선 |
| 5 | 일정 목록에서 'D-숫자'가 깨짐 | ✅ | xml에서 width = 'wrap_content'로 값 변경 |
| 6 | 일정명이 길어지는 경우 일정 표시 CardView 사이즈가 너무 커짐 | ✅ | android:maxLines='1' / android:ellipsize='end'로 일정이 길어지면 ... 으로 나오도록 개선 |
| 7 | 위젯 화면에서 '날짜' 깨짐 | ✅ | xml에서 width = 'wrap_content'로 값 변경 |
| 8 | 위젯 화면에서 불필요한 불투명 여백 개선 필요 | ✅ | 불투명한 배경 제거 |
| 9 | 위젯 사이즈가 너무 커서 활용성이 낮음 | ✅ | xml에서 padding=10dp 정도로 여백 줄임 |
| 10 |  |  | 날짜와 D-day가 한 줄로 나타나도록 수정 |
| 11 |  |  | 아이콘이 progressbar 내에 위치하며 끝 부분에 나타나도록 수정 |
| 12 | 위젯에 대표 일정으로 설정되어 있는 경우 일정을 삭제해도 위젯에 그대로 남음 | ✅ | 위젯 생성할 때 사용자가 일정을 선택할 수 있는 방식으로 수정 |
| 13 | 일정 삭제 기능만 있어서 불편 | ✅ | 일정 수정 기능 추가 |
| 14 | 위젯 생성 시 강제 종료되는 문제 | ✅ | null 데이터 불러오는 문제 개선
| 15 | 앱 재설치 시, 캐시가 남아있어서 이전 데이터가 그대로 나타나는 문제 | ✅ | 앱 최초 실행 시 DB&SharedPreferences 삭제 구현 / 캐시 삭제하도록 개선 |
| 16 | 진행률 계산 개선 | ✅ | 시작 일정을 사용자가 선택할 수 있게 하여 progress = (오늘 날짜 - 시작 날짜) / (이벤트 날짜 - 시작 날짜) 로 계산되도록 개선 |



<br><br>
