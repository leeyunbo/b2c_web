# b2c 쇼핑몰 개발 

## 기술 스택 
* spring-boot
* jpa-hibernate
* h2-database 
* thyme-leaf 
* gradle

<br/>

## 기능 
### 1. 회원 기능 (MemberService)
  * 회원  등록 O
  * 회원  조회 O
  * 회원  수정 O
### 2. 상품 기능 (ItemService)
  * 상품  등록 O
  * 상품  수정 O
  * 상품  조회 O
### 3. 주문 기능 (OrderService)
  * 상품  주문 O
  * 주문  내역 조회 O 
  * 주문  취소 O
  * 주문  수정 O
### 4. 기타 요구사항 
  * 상품은 재고 관리가 필요하다. O
  * 상품의 종류는 도서, 음반, 영화가 있다. 상품을 카테고리로 구분할 수 있다. O
  * 상품 주문시 배송 정보를 입력할 수 있다. O
  * VIP 사용자는 10% 할인을 받을 수 있다. O
  * 관리자 계정과 일반 계정으로 나뉘어진다. O
  * 관리자 계정은 모든 기능을 볼 수 있으며, 일반 계정은 상품 주문, 주문 내역 기능, 회원 가입 기능만 사용할 수 있다.
  
<br/>
  
## 테이블 설계 
<img width="904" alt="스크린샷 2021-01-28 오후 9 57 47" src="https://user-images.githubusercontent.com/44944031/106141731-e8efc500-61b3-11eb-882d-01c1ab920885.png">

<br/>

## 애플리케이션 아키텍처 
![스크린샷 2021-02-05 오후 11 39 01](https://user-images.githubusercontent.com/44944031/107047661-6a2a0600-680b-11eb-9306-53c2845a18ca.png)
1. controller, web : 웹 계층
2. service : 비즈니스 로직, 트랜잭션 처리 
3. repository : JPA를 직접 사용하는 계층, 엔티티 매니저 사용 
4. domain : 엔티티가 모여있는 계층, 모든 계층에서 사용 
 
