# import requests
# from bs4 import BeautifulSoup
#
# # 웹페이지 URL
# url = 'https://composecoffee.com/menu/category/185'
#
# # requests를 사용하여 웹페이지의 내용을 가져옴
# response = requests.get(url)
# data = response.text
#
# # BeautifulSoup 객체 생성
# soup = BeautifulSoup(data, 'html.parser')
#
# # 크롤링 결과를 저장할 배열
# image_srcs = []
# titles = []
#
# # 모든 itemBox를 찾음
# item_boxes = soup.find_all('div', class_='itemBox')
#
# # 각 itemBox 내에서 이미지 소스와 타이틀을 추출
# for box in item_boxes:
#     # 이미지 src 추출
#     image_tag = box.find('img')
#     if image_tag and 'src' in image_tag.attrs:
#         image_src = image_tag.attrs['src']
#         # 이미지 URL을 완성하기 위해 도메인 이름을 추가
#         image_srcs.append('https://composecoffee.com' + image_src)
#
#     # 타이틀 추출
#     title_tag = box.find('h4', class_='title')
#     if title_tag:
#         titles.append(title_tag.text.strip())
#
# # 결과 출력
# for img, title in zip(image_srcs, titles):
#     print(title, img)


import requests
from bs4 import BeautifulSoup

# 크롤링 결과를 저장할 배열
images = []
names = []

# 185부터 193페이지까지 크롤링
for page_number in range(185, 194):
    # 웹페이지 URL
    url = f'https://composecoffee.com/menu/category/{page_number}'

    # requests를 사용하여 웹페이지의 내용을 가져옴
    response = requests.get(url)
    data = response.text

    # BeautifulSoup 객체 생성
    soup = BeautifulSoup(data, 'html.parser')

    # 모든 itemBox를 찾음
    item_boxes = soup.find_all('div', class_='itemBox')

    # 각 itemBox 내에서 이미지 소스와 타이틀을 추출
    for box in item_boxes:
        # 이미지 src 추출
        image_tag = box.find('img')
        if image_tag and 'src' in image_tag.attrs:
            image_src = image_tag.attrs['src']
            # 이미지 URL을 완성하기 위해 도메인 이름을 추가
            images.append('https://composecoffee.com' + image_src)

        # 타이틀 추출
        title_tag = box.find('h4', class_='title')
        if title_tag:
            names.append(title_tag.text.strip())

# 결과 출력
for img, title in zip(images, names):
    print(title, img)
