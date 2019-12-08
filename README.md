# 근거리 무선 통신 기반의 학생 무인 인증 시스템

__강원대학교 학생 무인 인증시스템 구현__

## 개요
>학생들은 평일 야간 및 주말, 휴일에 학교에 출입하려면 출입 인증이 된 학생증이 필요하다.<br>
하지만 일부 학생들은 학생증을 안만들었거나 출입 인증이 안된 학생증을 가졌고 또는 아예 들고 다니지를 않는다.<br>
학생증은 안들고다녀도 휴대폰은 들고 다니니까 학생증 기능을 하는 것을 만들고자 한다.<br>

## 역할
> API를 사용한 Android app 개발<br>
> Spring boot Server 연결<br>
> NFC 센서 프로그래밍<br>

## 기술 스택
>MariaDB, Android Studio, Java, PHP

## KFC_master
>KFC-master는 NFC System을 제공하기 위해서 사용하는 어플리케이션이다.<br>
이는 사용자 로그인, 회원가입, NFC 서버 통신 및 데이터 전송이 가능하도록 한다.<br>

## KFC_Reader
>KFC-Reader는 NFC System을 사용하기 위해서 KFC-master를 사용하는 사용자가<br>
NFC 통신을 사용할때 데이터를 읽어, 이를 서버에 전송을 도와주는 데모 어플리케이션이다.<br>
이를 사용하여 굳이 NFC Reader기가 존재하지 않아도 어떠한 작동을 하는 지 확인할 수 있다.<br>

## 결과
> Android application 개발<br>
> API를 사용한 NFC, fingerprint의 사용<br>
> fingerprint를 하기 위한 sharedpreferences login<br>
> 부족함을 깨닫고 이후에 삭제 및 수정을 거친 이후에 커밋 예정<br>

## 기간
>2019년 7월 말 ~ 2019년 11월 19일


----------------------------------------------------------------------
# Server side
>https://github.com/2silvermoon/Nakji

----------------------------------------------------------------------

# 다이어그램

__Android app & Server System__
![diagram2](https://user-images.githubusercontent.com/41769568/70391157-689d5c00-1a15-11ea-9585-ebe618ce7830.JPG)
----------------------------------------------------------------------

__Android app work__
![diagram](https://user-images.githubusercontent.com/41769568/70390882-8c12d780-1a12-11ea-9379-86aaf70d6717.png)


----------------------------------------------------------------------

# 실행화면

__로그인__

<img src="https://user-images.githubusercontent.com/41769568/70390928-f3308c00-1a12-11ea-96bb-adfcc4108e25.png" width="230" height="400"></img>
<img src="https://user-images.githubusercontent.com/41769568/70390929-f461b900-1a12-11ea-8f8b-b9f34fea0b39.png" width="230" height="400"></img>

----------------------------------------------------------------------

__회원가입__

<img src="https://user-images.githubusercontent.com/41769568/70390953-28d57500-1a13-11ea-9fd4-d93f0bce11d7.png" width="230" height="400"></img>
----------------------------------------------------------------------


__로딩화면__

<img src="https://user-images.githubusercontent.com/41769568/70390939-11968780-1a13-11ea-9176-239e89b1d06c.png" width="230" height="400"></img>
----------------------------------------------------------------------


__메인화면 및 NFC통신__

<img src="https://user-images.githubusercontent.com/41769568/70390948-1f4c0d00-1a13-11ea-97e4-4a1b3af27ddc.png" width="230" height="400"></img>
<img src="https://user-images.githubusercontent.com/41769568/70390949-207d3a00-1a13-11ea-8721-3bf4c8c04fa2.png" width="230" height="400"></img>


