# Effective Java

Java 支持 4 种类型：接口、类（包括 enum）、数组和基本类型。

把焦点放在 API 设计上，对于重构过程是多么有益。重构的基本目标是改进系统结构，以避免代码重复。  
如果系统的组件没有设计良好的 API，要达到这样的目标则是不可能的。

清晰性和简洁性最为重要：组件的用户永远也不应该被其行为所迷惑。  
代码应该被重用，而不是被拷贝。  
组件之间的依赖尽可能降到最小。  
错误应该尽早被检测出来。  
编写出清晰、正确、可用、健壮、灵活和可维护的程序。  

## 2.创建和销毁对象

1. 用静态工厂方法代替构造器
   - 优势
      1. 静态工厂方法它们有名称，可以更具语义
      2. 不必在每次调用它们的时候都创建一个新对象
      3. 它们可以返回原类型的任何子类型的对象
      4. 所返回的对象的类可以随着每次调用而发生变化，取决于静态工厂方法的参数值
      5. 方法返回对象所属的类，在编写包含该静态工厂方法时可以不存在（服务提供者框架）
   - 缺点
      1. 如果类不含公有的/受保护的构造器，就不能被子类化
      2. 程序员很难发现那些静态工厂方法
   - 静态工厂惯用名称
      1. from, of, valueOf, instance, getInstance, create, newInstance
      2. getType, newTYpe, type
2. 遇到多个构造器参数时考虑使用构建器（Builder）
   - 优势
      1. Builder 模式模拟了具名的可选参数，易于编写和阅读
      2. Builder 模式也适用于类层次结构
   - 缺点
      1. 为了创建对象，必须先创建它的构建器，可能有性能问题
3. 用私有构造器或枚举类型强化 Singleton 属性
   - 私有构造器 + 导出公有静态成员
   - 私有构造器 + 导出公有静态工厂方法（静态工厂方法的优势在于提供了灵活性）
   - 单元素的枚举类型
4. 通过私有构造器强化不可实例化的能力  
   - 企图通过将类做成抽象类来强制该类不可被实例化是行不通的
   - 让这个类包含一个私有构造器，它就不能被实例化（比如工具类就不希望被实例化，它只有静态方法和静态域）
5. 优先考虑依赖注入来引用资源
   - 静态工具类和 Singleton 类不适合与需要引用底层资源的类（资源可能有多种）
   - 当创建一个新的实例时，就将该资源传到构造器中
   - 可使用依赖注入框架（如 Spring）来解决手动注入的凌乱
6. 避免创建不必要的对象  
   - 自动装箱使得基本类型和装箱基本类型之间的差别变得模糊起来，但是并没有完全消失
   - 要优先使用基本类型而不是装箱基本类型，要当心无意识的自动装箱
   - 通过维护自己的对象池来避免创建对象并不是一种好的做法，除非对象池中的对象是非常重量级的
   - 因重用对象而付出的代价要远远大于因创建重复对象而付出的代价，前者会导致潜在的 Bug 和安全漏洞，后者只会影响程序风格和性能
7. 消除过期的对象引用
   - 无意识的对象保持，在支持垃圾回收语言中是很隐蔽的内存泄漏
   - 清空对象引用应该是一种例外，而不是一种规范行为
   - 只要类是自己管理内存，程序员就应该警惕内存泄漏问题
   - 内存泄漏的另一个场景来源是缓存
   - 内存泄漏的第三个常见来源是监听器和其他回调
   - 可借助 Heap Profiler 发现内存泄漏问题
8. 避免使用终结方法和清除方法
   - 终结方法（finalizer）通常是不可预测的，也是危险的，一般情况下是不必要的
   - 在 Java9 中庸清除方法（cleaner）代替了 finalizer，清除方法没有终结方法那么危险，但仍然是不可预测、运行缓慢，一般情况下也是不必要的
   - 终结方法和清除方法的缺点在于不能保证会被及时执行，Java规范根本不保证它们会被执行
   - 注重时间的任务不应该由终结方法或者清除方法来完成
   - 永远不应该依赖终结方法或清除方法来更新重要的持久状态
   - 使用终结方法和清除方法有一个非常严重的性能损失
   - 为了防止非 final 类受到终结方法攻击，要编写一个空的 final 的 finalize 方法
   - 终结方法和清除方法的好处有：1.当资源所有者忘记调用它的 close 方法时，终结方法或清除方法可充当"安全网"；2.与对象的本地对等体（native peer）有关，可用来清除非关键的本地方法所拥有的资源
9. try-with-resources 优先于 try-finally
   - try-with-resources 更简洁易懂，也更容易诊断
10. 覆盖 equals 时请遵循通用约定
   - 自反性：x.equals(x) == true，对称性：x.equals(y) == y.equals(x)，传递性，
   - 使用 == 检查参数是否为这个对象的引用；使用 instanceof 检查参数是否为正确的类型；把参数转换为正确的类型；对于该类中的每个关键域检查是否相匹配
   - 覆盖 equals 时总是要覆盖 hashCode；不要企图让 equals 方法过于智能；不要将 equals 声明中的 Object 对象替换为为他类型
11. 覆盖 equals 时总是要覆盖 hashCode
   - 相等的对象必须具有相等的散列码（hash code）
   - 不要试图从散列码计算中排除掉一个对象的关键域来提高性能
   - 不要对 hashCode 方法的返回值做出具体的规定，因此客户端无法理所当然的依赖它；这样可以为修改提供灵活性
12. 始终要覆盖 toString
   - 提供好的 toString 实现可以让类使用起来更加舒适、更易于调试
   - toString 方法应该返回对象中包含的所有值得关注的信息
   - 无论是否决定指定格式，都应该在文档中明确地表明你的意图
   - 为 toString 返回值中包含的信息提供一种可通过编程访问的途径（getter）
13. 谨慎地覆盖 clone
   - 实现 Cloneable 接口的类是为了提供一个功能适当的公有 clone 方法
   - 不可变的类永远都不应该提供 clone 方法
   - clone 方法就是另一个构造器；必须确保它不会伤害到原始的对象，并确保正确地创建被克隆对象中的约束条件
   - Cloneable 架构与引用可变对象的 final 域的正常用法是不相兼容的
   - 公有的 clone 方法应该省略 throws 声明（不抛出受检查一次的方法使用起来更轻松）
   - 对象拷贝的更好的办法是提供一个拷贝构造器或拷贝工厂