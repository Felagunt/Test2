��#   T e s t 2 
 
1.auth and courses is separated modules<br/>
2.all navigation goes through RootNavGraph, there's two separet nav graphs<br/>
3.RootNavGraph contains auth graph and and separated graph BottomBar in whitch bottomnav menu with 3 screens. All lays in app../core/route and assembles there as well<br/>
4.All routes placed in Route app../core/route<br/>
5.data for courses goes from assets/courses.json, throught retrofit and castom Interceptor(LocalJsonInterceptor), and later repo and so on<br/>
6.Database dont work:)<br/>
7.What TODO fix db, create and separete code in core\core-ui modules<br/>
