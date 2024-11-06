# java-convenience-store-precourse
## 기능 목록
### 입력 기능
- 구매할 상품과 수량 입력
  - `[상품1-n],[상품2-m],...` 형식
- 프로모션 적용 상품에 대해 해당 수보다 적게 가져오면 상품 추가 여부 입력
  - `Y / N` 형식
- 프로모션 재고가 부족하여, 일부 수량 정가 결제 여부 입력
  - `Y / N` 형식
- 멤버십 할인 적용 여부
  - `Y / N` 형식

### 출력 기능
- 환영인사, 상품명, 가격, 프로모션 이름, 재고 안내
- 프로모션 적용 상품에 대해 해당 수보다 적게 가져오면 안내 메시지
  - `현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)`
- 프로모션 재고가 부족하여, 일부 수량을 정가로 결제해야하는 경우 안내 메시지
  - `현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)`
- 멤버십 할인 안내 메시지
  - `멤버십 할인을 받으시겠습니까? (Y/N)`
- 구매 상품 내역, 증정 상품 내역, 금액 정보 출력
- 추가 여부 확인 메시지 출력
  - `감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)`

### 예외 상황
- 구매 형식이 올바르지 않은 경우
- 존재 하지 않는 상품을 적은 경우
- 구매 수량이 재고를 초과한 경우
- Y / N 에 해당하지 않은 경우

### 편의점 시스템 기능
- 재고 관리
  1. 사용자가 구매하면
  2. 결제 가능 여부 확인 
  3. 그만큼 차감 및 저장
  
- 프로모션 할인
  1. 구매 날짜와 프로모션 기간 비교, 할인 가능 여부 확인
  2. 프로모션 상품에 대해 고객이 증정 상품을 안가져온 경우 안내
  2. 프로모션 재고 내에서만 구매
  3. 프로모션 재고 부족일 경우, 일반 재고 판매 및 정가 구매 안내

- 멤버십 할인
  1. 프로모션 미적용 금액의 30% 할인
  2. 프로모션 적용 후 남은 금액에 할인 적용
  2. 최대 할인 한도 8,000원

- 영수증 출력
  - 구매 상품 내역: 구매한 상품명, 수량, 가격
  - 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
  - 금액 정보
    - 총구매액: 구매한 상품의 총 수량과 총 금액
    - 행사할인: 프로모션에 의해 할인된 금액
    - 멤버십할인: 멤버십에 의해 추가로 할인된 금액
    - 내실돈: 최종 결제 금액