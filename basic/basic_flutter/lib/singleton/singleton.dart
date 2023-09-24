class Singleton {
  // 私有的静态变量，保存类的唯一实例
  static final Singleton _singleton = Singleton._internal();

  // 私有的构造函数
  Singleton._internal();

  // 公开的静态方法，返回类的唯一实例
  factory Singleton() {
    return _singleton;
  }
}
