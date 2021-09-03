# Muhit - Closest Point of Interest

This application is a reference application prepared to introduce the use of Map Kit, Location Kit and Site Kit used for location based operations within the Huawei Mobile Services (HMS) ecosystem.

<img src="https://developerfile7.hicloud.com/FileServer/getFile/7/appAttachtemp/20210120/appAttach/023/106/224/0890090000023106224.20210120101119.89307180786467536338565329704114:20210120101120:2500:DBABF9175A90165A8369292D000E4E3C02222DDD191A13215C54F76D7E71CBD2.jpg" width=250></img>
<img src="https://developerfile7.hicloud.com/FileServer/getFile/7/appAttachtemp/20210120/appAttach/023/106/224/0890090000023106224.20210120101119.14809235858361961375817847815161:20210120101123:2500:5FFCA91E8584780F0AECAB6D58E55B721E0C00D32FEDA962A55DA2A5C455F32B.jpg" width=250></img>
<img src="https://developerfile7.hicloud.com/FileServer/getFile/7/appAttachtemp/20210120/appAttach/023/106/224/0890090000023106224.20210120101119.91525843330760138492997681146575:20210120101123:2500:1524928BFC0942F4ED2096728732B4DE0770B4E79696374DDEC9C053E26C0BDF.jpg" width=250></img>

## Introduction

Muhit is an app that showing on map closest points of interest from your location. You can select multiple points on list.


## About HUAWEI Location Kit

HMS Location Kit combines the Global Navigation Satellite System (GNSS), Wi-Fi, and base station location functionalities into your app to build up global positioning capabilities, allowing you to provide flexible location-based services for global users. Currently, it provides three main capabilities: fused location, activity identification, and geofence. You can call one or more of these capabilities as needed.
[More Details](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/introduction-0000001050706106)

## About HUAWEI Map Kit

HMS Map Kit is an SDK for map development. It covers map data of more than 200 countries and regions, and supports over one hundred of languages. With this SDK, you can easily integrate map-based functions into your apps.
[More Details](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides-V5/android-sdk-brief-introduction-0000001061991343-V5)


## About HUAWEI Site Kit

HMS Site Kit provides place search services including keyword search, nearby place search, place detail search, and place search suggestion, helping your app provide convenient place-related services to attract more users and improve user loyalty.
[More Details](https://developer.huawei.com/consumer/en/doc/development/HMSCore-Guides/android-sdk-introduction-0000001050158571)


## What You Will Need

**Hardware Requirements**
- A computer that can run Android Studio.
- An Android phone for debugging.

**Software Requirements**
- Android SDK package
- Android Studio 3.X
- HMS Core (APK) 4.X or later

## Getting Started

Muhit uses HUAWEI services. In order to use them, you have to [create an app](https://developer.huawei.com/consumer/en/doc/distribution/app/agc-create_app) first. Before getting started, please [sign-up](https://id1.cloud.huawei.com/CAS/portal/userRegister/regbyemail.html?service=https%3A%2F%2Foauth-login1.cloud.huawei.com%2Foauth2%2Fv2%2Flogin%3Faccess_type%3Doffline%26client_id%3D6099200%26display%3Dpage%26flowID%3D6d751ab7-28c0-403c-a7a8-6fc07681a45d%26h%3D1603370512.3540%26lang%3Den-us%26redirect_uri%3Dhttps%253A%252F%252Fdeveloper.huawei.com%252Fconsumer%252Fen%252Flogin%252Fhtml%252FhandleLogin.html%26response_type%3Dcode%26scope%3Dopenid%2Bhttps%253A%252F%252Fwww.huawei.com%252Fauth%252Faccount%252Fcountry%2Bhttps%253A%252F%252Fwww.huawei.com%252Fauth%252Faccount%252Fbase.profile%26v%3D9f7b3af3ae56ae58c5cb23a5c1ff5af7d91720cea9a897be58cff23593e8c1ed&loginUrl=https%3A%2F%2Fid1.cloud.huawei.com%3A443%2FCAS%2Fportal%2FloginAuth.html&clientID=6099200&lang=en-us&display=page&loginChannel=89000060&reqClientType=89) for a HUAWEI developer account.

After creating the application, you need to [generate a signing certificate fingerprint](https://developer.huawei.com/consumer/en/codelab/HMSPreparation/index.html#3). Then you have to set this fingerprint to the application you created in AppGallery Connect.
- Go to "My Projects" in AppGallery Connect.
- Find your project from the project list and click the app on the project card.
- On the Project Setting page, set SHA-256 certificate fingerprint to the SHA-256 fingerprint you've generated.
![AGC-Fingerprint](https://communityfile-drcn.op.hicloud.com/FileServer/getFile/cmtyPub/011/111/111/0000000000011111111.20200511174103.08977471998788006824067329965155:50510612082412:2800:6930AD86F3F5AF6B2740EF666A56165E65A37E64FA305A30C5EFB998DA38D409.png?needInitFileName=true?needInitFileName=true?needInitFileName=true?needInitFileName=true)

## Using the Application

- When you open the application, you will see a full screen map. This map is provided with the Huawei Map Kit.
- If you grant location permission, Huawei Location Kit provides instant location information.
- Your obtained location information is sent to Huawei Site Kit to reach the closest POIs to your location. (POI: Point of Interest)
- You choose the type of POI you want to see from the drop-down box on the screen, and thanks to the data provided by Huawei Site Kit, the nearest POI list is created.
- The information in this list is processed and added to the Huawei Map as a marker (pin).
- For example, when you select ATM from the list, you can quickly see the ATMs closest to you on the map.


## Screenshots
...

## Project Structure

Muhit is designed as simple as possible to highlight the use of HMS Kits.

## Libraries

- Huawei Location Kit
- Huawei Map Kit
- Huawei Site Kit

## Contributors

- Murat Yüksektepe