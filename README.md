# java-blackjack
블랙잭 게임 미션 저장소
### *2020.03.10~2020.03.13*

---

# README.md

## ⏰ 시간

---

- 페어 프로그래밍 시간 : 12pm(점심먹고 모이자) ~ 6pm
- 페어 프로그래밍 사이클 : 1시간 하고 10분 쉬는시간
- 페어 전환하는시간 : 기능단위로 경험해보고 별로면 시간을 정해보기로

## ✔ git commit message convention

---

{*keyword*} : 한글 커밋 메세지 작성

### keyword

- refactor :
- feat :
- fix :
- style :
- docs :
- test :

## 🔒 TDD method naming convention

---

MethodName_StateUnderTest_ExpectedBehavior

- @DisplayName 추가 작성
- 메소드명_테스트 하려는 상태_기대하는 동작
- **어떤 이름의 메소드**에 **해당하는 상태**로 테스트를 진행하면 **이러한 동작이 발생**할 것이라고 기대하는 순서로 작성
- 예시
    - isAdult_AgeLessThan18_False
    - withdrawMoney_InvalidAccount_ExceptionThrown
    - admitStudent_MissingMandatoryFields_FailToAdmit

# 기능 요구사항

- 블랙잭 게임을 변형한 프로그램을 구현한다. 블랙잭 게임은 딜러와 플레이어 중 카드의 합이 21 또는 21에 가장 가까운 숫자를 가지는 쪽이 이기는 게임이다.
- ~~플레이어는 게임을 시작할 때 배팅 금액을 정해야 한다.~~ 카드의 숫자 계산은 카드 숫자를 기본으로 하며, 예외로 Ace는 1 또는 11로 계산할 수 있으며, King, Queen, Jack은 각각 10으로 계산한다.
- 게임을 시작하면 플레이어는 두 장의 카드를 지급 받으며, 두 장의 카드 숫자를 합쳐 21을 초과하지 않으면서 21에 가깝게 만들면 이긴다. 21을 넘지 않을 경우 원한다면 얼마든지 카드를 계속 뽑을 수 있다. 단, 카드를 추가로 뽑아 21을 초과할 경우 배팅 금액을 모두 잃게 된다.
- 딜러는 처음에 받은 2장의 합계가 16이하이면 반드시 1장의 카드를 추가로 받아야 하고, 17점 이상이면 추가로 받을 수 없다.
- 게임을 완료한 후 각 플레이어별로 승패를 출력한다.
```
    게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)
    pobi,jason
    
    딜러와 pobi, jason에게 2장의 나누었습니다.
    딜러: 3다이아몬드
    pobi카드: 2하트, 8스페이드
    jason카드: 7클로버, K스페이드
    
    pobi는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)
    y
    pobi카드: 2하트, 8스페이드, A클로버
    pobi는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)
    n
    jason은 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)
    n(y)
    jason카드: 7클로버, K스페이드
    jason은 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n) - n이 나올 때 까지(21을 넘지 않을 경우 원한다면 얼마든지 카드를 계속 뽑을 수 있다.)
    
    
    딜러는 16이하라 한장의 카드를 더 받았습니다.
    
    딜러 카드: 3다이아몬드, 9클로버, 8다이아몬드 - 결과: 20
    pobi카드: 2하트, 8스페이드, A클로버 - 결과: 21
    jason카드: 7클로버, K스페이드 - 결과: 17
    
    ## 최종 승패
    딜러: 1승 1패
    pobi: 승 
    jason: 패
```
![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e415d8ff-9627-49d5-9568-fd98414e4135/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e415d8ff-9627-49d5-9568-fd98414e4135/Untitled.png)

# 설계

![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/55e7f700-1a6b-4e98-a2f5-ca57b1f22a0d/Untitled.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/55e7f700-1a6b-4e98-a2f5-ca57b1f22a0d/Untitled.png)

### 용어 정리

- **블랙잭(black jack)** 처음 받은 카드 두 장의 합이 21일 경우
    - Ace 한 장과 10, J, Q, K로 이루어진 카드를 받은 경우
    - 베팅금액의 1.5배를 돌려준다.
- **버스트(bust)** 카드 숫자의 합이 21을 초과하는 경우
- **힛(hit)** 처음 2장의 상태에서 카드를 더 뽑는 것
- **스탠드, 스테이(stand, stay)** 카드를 더 뽑지 않고 차례를 마치는 것
- **푸쉬(push)** 플레이어와 딜러가 무승부인 경우

### 구현 기능 목록

1. 이름을 입력 받는다.
    - 쉼표 기준으로 입력 받는다. 이 때, 공백은 모두 제거한다.
    - 서로 중복 불가
    - "딜러"랑 같아도 안됨
    - 한 명 이상(빈 문자열, null 허용X)
2. 딜러와 참가자들은 카드를 2장씩 나눠 가진다.
3. 참가자가 갖는 카드의 결과를 계산한다.
4. 나눠 가진 카드를 출력한다. 
5. 참가자들에게 카드를 받을 것인지 물어본다.
    - 받는 경우
        1. 받은 카드를 보여준다. 
        2. 참가자가 갖는 카드의 결과를 계산한다.
            1. 참가자가 갖는 카드의 결과가 21을 초과하는 경우, 게임을 끝낸다.
            2. 21이 넘지 않을 경우 다시 받을 것인지 물어본다
                1. y가 입력되면 5번 반복
                2. n이 입력되면 그만 물어본다.
    - 안받는 경우

        다음 참가자에게 물어본다.

6. 딜러가 더 받을지 판단
    1. 16이하면 1장을 추가로 받는다.(17 이상이면 추가로 받을 수 없다.)
7. 딜러와 참가자의 카드 목록과 합계를 출력한다.
8. 최종 승패를 계산한다. 
    1. 딜러의 승패 결과 갯수는 참여자의 수와 일치해야한다. 
    2. 딜러 21 참여자 21 → 무승부

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)