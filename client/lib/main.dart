import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:get/get.dart';
import 'package:techub/presentation/routes/routes.dart';
import 'package:techub/presentation/screen/home/home_binding.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'presentation/routes/pages.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    SystemChrome.setEnabledSystemUIMode(SystemUiMode.manual,
        overlays: [SystemUiOverlay.bottom]);
    return ScreenUtilInit(
        designSize: const Size(390, 844),
        builder: ((context, child) {
          return GetMaterialApp(
            debugShowCheckedModeBanner: false,
            theme: ThemeData(
              scaffoldBackgroundColor: Colors.white,
              fontFamily: 'Pretendard',
            ),
            builder: (context, child) => MediaQuery(
              data: MediaQuery.of(context)
                  .copyWith(textScaler: const TextScaler.linear(1.0)),
              child: child!,
            ),
            supportedLocales: const [
              Locale('ko', 'KR'),
            ],
            localizationsDelegates: const [
              GlobalMaterialLocalizations.delegate,
              GlobalCupertinoLocalizations.delegate
            ],
            getPages: Pages.routes,
            initialRoute: Routes.home,
            initialBinding: HomeBinding(),
            smartManagement: SmartManagement.full,
            navigatorKey: Get.key,
          );
        }));
  }
}
