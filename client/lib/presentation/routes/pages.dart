import 'package:get/get.dart';
import 'package:techub/presentation/routes/routes.dart';
import '../screen/home/home_binding.dart';
import '../screen/home/home_screen.dart';

class Pages {
  static final routes = [
    GetPage(
      name: Routes.home,
      page: () => const HomeScreen(),
      binding: HomeBinding(),
    ),
  ];
}
