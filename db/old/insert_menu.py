import requests
import pymysql
from bs4 import BeautifulSoup

# 음료 카테고리 별 URL 주소
# 185: 커피 (1)
# 186: 더치커피 (2)
# 187: 논커피라떼 (3)
# 188: 주스 (4)
# 189: 스무디 (5)
# 190: 티 (6)
# 191: 에이드 (7)
# 192: 프라페 (8)
# 193: 밀크쉐이크 (9)

# 웹페이지 URL
url = 'https://composecoffee.com/menu/category/185'

# 우리 이정도 노가다는 합시다... 귀찮스..
menu_category_id = 1  # 185=1 / 186=2 / 187=3 / ...

# requests를 사용하여 웹페이지의 내용을 가져옴
response = requests.get(url)
data = response.text

# BeautifulSoup 객체 생성
soup = BeautifulSoup(data, 'html.parser')

# 크롤링 결과를 저장할 배열
image_srcs = []
titles = []

# 모든 itemBox를 찾음
item_boxes = soup.find_all('div', class_='itemBox')

# 각 itemBox 내에서 이미지 소스와 타이틀을 추출
for box in item_boxes:
    # 이미지 src 추출
    image_tag = box.find('img')
    if image_tag and 'src' in image_tag.attrs:
        image_src = image_tag.attrs['src']
        # 이미지 URL을 완성하기 위해 도메인 이름을 추가
        image_srcs.append('https://composecoffee.com' + image_src)

    # 타이틀 추출
    title_tag = box.find('h4', class_='title')
    if title_tag:
        titles.append(title_tag.text.strip())

# 결과 출력
for img, title in zip(image_srcs, titles):
    print(title, img)





# RDMBS : MySQL 메뉴 데이터 삽입

# MySQL 데이터베이스에 연결 설정
connection = pymysql.connect(host='127.0.0.1',
                             user='ssafy',
                             password='ssafy',
                             database='s10p12c209',
                             charset='utf8mb4',
                             cursorclass=pymysql.cursors.DictCursor)


try:
    with connection.cursor() as cursor:
        # INSERT 문을 반복해서 실행
        for name, image in zip(titles, image_srcs):
            sql = """
            INSERT INTO menus (menu_category_id, menu_deleted, menu_image, menu_name, menu_price)
            VALUES (%s, %s, %s, %s, %s)
            """
            cursor.execute(sql, (menu_category_id, False, image, name, 1500))

    # 데이터베이스에 변경사항 저장
    connection.commit()

finally:
    connection.close()
