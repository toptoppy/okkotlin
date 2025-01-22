package toptoppy.kotlin.training

fun main() {
    println("Why Kotlin?")
    println("1. Concise and Expressive")
    println("   Kotlin reduces boilerplate compared to Java, making code shorter and easier to read.")
    println("2. Null Safety")
    println("   Kotlin's type system prevents many common NullPointerExceptions by design.")
    println("3. Interoperable with Java")
    println("   You can use Java libraries and frameworks right out of the box.")
    println("4. Modern Features")
    println("   Kotlin supports coroutines for asynchronous programming, extension functions, and more.")
    println("5. Official for Android")
    println("   Google recommends Kotlin for Android development, ensuring ongoing support and community growth.")
}

val languageThatInfluencedKotlin = """
    Kotlin draws its ideas and features from many languages

    FORTRAN: FORmula TRANslation (1957)
        - Fortran libraries: encode equations and manage scientific computations

    LISP: LISt Processor (1958)
        - First functional programming language
        - GNU Emacs editor is written primarily in Lisp

    ALGOL: ALGOrithmic Language (1958)
        - Introduced block structure and a more readable syntax

    COBOL: COmmon Business-Oriented Language (1959)
        - Designed for business data processing
        - Emphasized English-like syntax, readability, and record-based file handling

    BASIC: Beginners' All-purpose Symbolic Instruction Code (1964)
        - Intended for teaching programming to beginners
        - One of the first “open” programming languages, accessible to a wide audience

    Simula 67, the Original Object-Oriented Language (1967)
        - First language to introduce the concept of classes and objects

    Pascal (1970)
        - Promoted structured programming and strong typing
        - Often used for teaching programming fundamentals

    C (1972)
        - Low-level systems programming language
        - Influenced many future languages with its syntax and use of braces

    Smalltalk (1972)
        - One of the purest object-oriented languages
        - Introduced the idea of everything being an object, including primitives

    C++: A Better C with Objects (1983)
        - Provided backwards-compatibility with C
        - Introduced object-oriented features to a systems-level language

    Python: Friendly and Flexible (1990)
        - A high-level programming language for everyone
        - One of the first mainstream languages to combine functional and OO features

    Haskell: Pure Functional Programming (1990)
        - Created as an open standard for exploring purely functional programming concepts

    JAVA: Virtual Machines and Garbage Collection (1995)
        - Popularized the “write once, run anywhere” approach via a virtual machine
        - Automated memory management through garbage collection

    JavaScript: Borrowed the Name of Java (1995)
        - Lightweight, multi-paradigm, prototype-based language for web development
        - Despite the name, it evolved independently from Java

    C#: Java for .NET (2000)
        - Introduced extension methods and LINQ
        - More functional features than Java
        - Influenced Kotlin’s design (e.g., extension functions)

    Scala: SCALAble (2003)
        - Runs on the JVM
        - An object-functional hybrid, strongly influencing Kotlin’s functional features

    Groovy: A Dynamic JVM Language (2007)
        - Influenced Kotlin’s safe-call operators (?.) and Elvis operator (?:)
        - Viewed as a dynamic, streamlined version of Java
""".trimIndent()

val kotlinMultiParadigm = """
    - Imperative programming
        Description:
        Kotlin supports the traditional step-by-step approach where you use 
        mutable variables, loops, and conditionals to change program state.

    - Functional programming
        Description:
        Kotlin provides higher-order functions, lambdas, and immutable data 
        structures, making it easy to write functional-style code. 

    - Object-oriented programming
        Description:
        Kotlin has classes, interfaces, and inheritance, allowing you to 
        encapsulate behavior and data within objects.

    - Reactive programming
        Description:
        While not native to Kotlin, reactive libraries (such as RxKotlin) 
        and coroutines facilitate building event-driven, asynchronous systems.

    - Declarative UI programming
        Description:
        Jetpack Compose (Android) and Kotlin-based frameworks enable a 
        declarative style for building user interfaces, focusing on what 
        the UI should look like rather than how to draw it.

    - DSL (Domain-Specific Language) creation
        Description:
        Kotlin’s expressive syntax, extension functions, and infix notation 
        make it well-suited for creating clean, readable DSLs.
""".trimIndent()

