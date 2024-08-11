
# Github Connect

## Get Started
Add your github access token into file `local.properties`
```
token="YOUR_GITHUB_TOKEN"
```
## Module


- app
- core/common
- data
- domain
- features
  - List User
  - User Detail


## File Tree
<details>
<summary>
File Tree
</summary>

```
┣ 📂app
┃ ┣ 📂src
┃ ┃ ┣ 📂main
┃ ┃ ┃ ┣ 📂java
┃ ┃ ┃ ┃ ┗ 📂com
┃ ┃ ┃ ┃   ┗ 📂riftar
┃ ┃ ┃ ┃     ┗ 📂githubconnect
┃ ┃ ┃ ┃       ┣ 📂di
┃ ┃ ┃ ┃       ┃ ┣ 📂module
┃ ┃ ┃ ┃       ┃ ┃ ┣ 📜DatabaseModule.kt
┃ ┃ ┃ ┃       ┃ ┃ ┣ 📜DataModule.kt
┃ ┃ ┃ ┃       ┃ ┃ ┣ 📜DomainModule.kt
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜ViewModelModule.kt
┃ ┃ ┃ ┃       ┃ ┗ 📜KoinInitializer.kt
┃ ┃ ┃ ┃       ┗ 📜MainApplication.kt
┃ ┃ ┃ ┗ 📜AndroidManifest.xml
┣ 📂core
┃ ┗ 📂common
┃   ┣ 📂src
┃   ┃ ┣ 📂main
┃   ┃ ┃ ┣ 📂java
┃   ┃ ┃ ┃ ┗ 📂com
┃   ┃ ┃ ┃   ┗ 📂riftar
┃   ┃ ┃ ┃     ┗ 📂common
┃   ┃ ┃ ┃       ┣ 📂base
┃   ┃ ┃ ┃       ┃ ┣ 📜BaseActivity.kt
┃   ┃ ┃ ┃       ┃ ┗ 📜LoadingDialog.kt
┃   ┃ ┃ ┃       ┣ 📂constant
┃   ┃ ┃ ┃       ┃ ┣ 📜NavigationConstant.kt
┃   ┃ ┃ ┃       ┃ ┗ 📜ViewConstant.kt
┃   ┃ ┃ ┃       ┣ 📂helper
┃   ┃ ┃ ┃       ┃ ┣ 📜NetworkConnectivityObserver.kt
┃   ┃ ┃ ┃       ┃ ┗ 📜ViewExtensions.kt
┃   ┃ ┃ ┃       ┗ 📂ui
┃   ┃ ┃ ┃         ┗ 📂theme
┃   ┃ ┃ ┃           ┣ 📜Color.kt
┃   ┃ ┃ ┃           ┣ 📜Theme.kt
┃   ┃ ┃ ┃           ┗ 📜Type.kt
┃   ┃ ┃ ┗ 📜AndroidManifest.xml
┣ 📂data
┃ ┣ 📂src
┃ ┃ ┣ 📂main
┃ ┃ ┃ ┣ 📂java
┃ ┃ ┃ ┃ ┗ 📂com
┃ ┃ ┃ ┃   ┗ 📂riftar
┃ ┃ ┃ ┃     ┗ 📂data
┃ ┃ ┃ ┃       ┣ 📂common
┃ ┃ ┃ ┃       ┃ ┣ 📂client
┃ ┃ ┃ ┃       ┃ ┃ ┣ 📜HttpClientProvider.kt
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜NetworkErrorInterceptor.kt
┃ ┃ ┃ ┃       ┃ ┣ 📂database
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜AppDatabase.kt
┃ ┃ ┃ ┃       ┃ ┗ 📂notes
┃ ┃ ┃ ┃       ┃   ┗ 📂room
┃ ┃ ┃ ┃       ┃     ┣ 📂dao
┃ ┃ ┃ ┃       ┃     ┃ ┗ 📜NotesDao.kt
┃ ┃ ┃ ┃       ┃     ┗ 📂entity
┃ ┃ ┃ ┃       ┃       ┗ 📜NotesEntity.kt
┃ ┃ ┃ ┃       ┣ 📂listuser
┃ ┃ ┃ ┃       ┃ ┣ 📂api
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜ListUserAPI.kt
┃ ┃ ┃ ┃       ┃ ┣ 📂mapper
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜ListUserMapper.kt
┃ ┃ ┃ ┃       ┃ ┣ 📂model
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜UserResponse.kt
┃ ┃ ┃ ┃       ┃ ┣ 📂pagingsource
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜ListUserPagingSource.kt
┃ ┃ ┃ ┃       ┃ ┣ 📂remotemediator
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜ListUserRemoteMediator.kt
┃ ┃ ┃ ┃       ┃ ┣ 📂repository
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜ListUserRepositoryImpl.kt
┃ ┃ ┃ ┃       ┃ ┗ 📂room
┃ ┃ ┃ ┃       ┃   ┣ 📂dao
┃ ┃ ┃ ┃       ┃   ┃ ┗ 📜ListUserDao.kt
┃ ┃ ┃ ┃       ┃   ┗ 📂entity
┃ ┃ ┃ ┃       ┃     ┗ 📜UserEntity.kt
┃ ┃ ┃ ┃       ┗ 📂userdetail
┃ ┃ ┃ ┃         ┣ 📂api
┃ ┃ ┃ ┃         ┃ ┗ 📜UserDetailAPI.kt
┃ ┃ ┃ ┃         ┣ 📂mapper
┃ ┃ ┃ ┃         ┃ ┗ 📜UserDetailMapper.kt
┃ ┃ ┃ ┃         ┣ 📂model
┃ ┃ ┃ ┃         ┃ ┗ 📜UserDetailResponse.kt
┃ ┃ ┃ ┃         ┣ 📂repository
┃ ┃ ┃ ┃         ┃ ┗ 📜UserDetailRepositoryImpl.kt
┃ ┃ ┃ ┃         ┗ 📂room
┃ ┃ ┃ ┃           ┣ 📂dao
┃ ┃ ┃ ┃           ┃ ┗ 📜UserDetailDao.kt
┃ ┃ ┃ ┃           ┗ 📂entity
┃ ┃ ┃ ┃             ┗ 📜UserDetailEntity.kt
┃ ┃ ┃ ┗ 📜AndroidManifest.xml
┣ 📂domain
┃ ┣ 📂src
┃ ┃ ┣ 📂main
┃ ┃ ┃ ┣ 📂cpp
┃ ┃ ┃ ┃ ┣ 📜CMakeLists.txt
┃ ┃ ┃ ┃ ┗ 📜domain.cpp
┃ ┃ ┃ ┣ 📂java
┃ ┃ ┃ ┃ ┗ 📂com
┃ ┃ ┃ ┃   ┗ 📂riftar
┃ ┃ ┃ ┃     ┗ 📂domain
┃ ┃ ┃ ┃       ┣ 📂listuser
┃ ┃ ┃ ┃       ┃ ┣ 📂model
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜User.kt
┃ ┃ ┃ ┃       ┃ ┣ 📂repository
┃ ┃ ┃ ┃       ┃ ┃ ┗ 📜ListUserRepository.kt
┃ ┃ ┃ ┃       ┃ ┗ 📂usecase
┃ ┃ ┃ ┃       ┃   ┗ 📜GetListUserUseCase.kt
┃ ┃ ┃ ┃       ┗ 📂userdetail
┃ ┃ ┃ ┃         ┣ 📂model
┃ ┃ ┃ ┃         ┃ ┗ 📜UserDetail.kt
┃ ┃ ┃ ┃         ┣ 📂repository
┃ ┃ ┃ ┃         ┃ ┗ 📜UserDetailRepository.kt
┃ ┃ ┃ ┃         ┗ 📂usecase
┃ ┃ ┃ ┃           ┣ 📜GetUserDetailUseCase.kt
┃ ┃ ┃ ┃           ┗ 📜SaveNotesUseCase.kt
┃ ┃ ┃ ┗ 📜AndroidManifest.xml
┣ 📂features
┃ ┣ 📂listuser
┃ ┃ ┣ 📂src
┃ ┃ ┃ ┣ 📂main
┃ ┃ ┃ ┃ ┣ 📂java
┃ ┃ ┃ ┃ ┃ ┗ 📂com
┃ ┃ ┃ ┃ ┃   ┗ 📂riftar
┃ ┃ ┃ ┃ ┃     ┗ 📂listuser
┃ ┃ ┃ ┃ ┃       ┣ 📜ListUserActivity.kt
┃ ┃ ┃ ┃ ┃       ┗ 📜ListUserViewModel.kt
┃ ┃ ┃ ┃ ┗ 📜AndroidManifest.xml
┃ ┗ 📂userdetail
┃   ┣ 📂src
┃   ┃ ┣ 📂main
┃   ┃ ┃ ┣ 📂java
┃   ┃ ┃ ┃ ┗ 📂com
┃   ┃ ┃ ┃   ┗ 📂riftar
┃   ┃ ┃ ┃     ┗ 📂userdetail
┃   ┃ ┃ ┃       ┣ 📂bottomsheet
┃   ┃ ┃ ┃       ┃ ┗ 📜EditNotesBottomSheet.kt
┃   ┃ ┃ ┃       ┣ 📜UserDetailActivity.kt
┃   ┃ ┃ ┃       ┗ 📜UserDetailViewModel.kt
┃   ┃ ┃ ┗ 📜AndroidManifest.xml
┣ 📜gradle.properties
┣ 📜gradlew
┣ 📜gradlew.bat
┗ 📜settings.gradle.kts
```
</details>

## Screenshots
<img src=".\screenshot\ss_list.png" alt="list_user" width="200"/> <img src=".\screenshot\ss_detail_light.png" alt="detail_user_light" width="200"/>
<img src=".\screenshot\ss_detail_dark.png" alt="detail_user_dark" width="200"/>
<img src=".\screenshot\ss_loading.png" alt="loading" width="200"/>
<img src=".\screenshot\screen_recording.gif" alt="screen_shot" width="200"/>
