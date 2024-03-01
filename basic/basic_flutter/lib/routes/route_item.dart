class RouteItem {
  String routeName;
  String routePath;
  String routeDescribe;

  RouteItem({
    required this.routeName,
    required this.routePath,
    this.routeDescribe = "",
  });

  factory RouteItem.fromJson(Map<String, dynamic> json) => RouteItem(
        routeName: json['routeName'],
        routePath: json['routePath'],
        routeDescribe: json['routeDescribe'],
      );

  Map<String, dynamic> toJson() => {
        'routeName': routeName,
        'routePath': routePath,
        'routeDescribe': routeDescribe,
      };
}
