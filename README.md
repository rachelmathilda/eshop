## Modul 3
### Refleksi

1. prinsip yang diterapkan: 
   - SRP (Single Responsibility Principle): suatu class harus memiliki 1 fungsi saja
     CarController dipisah dengan ProductController, begitu juga dengan CarRepository, CarService, dan CarServiceImpl yang dibuat berbeda dengan yang dimiliki product.
   - OCP (Open-Closed Principle): semua entitas software (kelas, modul, fungsi, dsb) terbuka untuk ekstensi tetapi tertutup untuk di modifikasi
     misal kita ingin menambah kelas baru berupa dress. saya tidak perlu mengubah fungsionalitas dari kelas dan modul dari entitas Car maupun Product
   - LSP (Liskov Substitution Principle): object di superclass harus bisa di gantikan dengan objek di subclass
     CarServiceImpl dan ProductServiceImpl yang merupakan subclasses yang mengimplementasikan semua objek yang ada di superclassnya.  
   - ISP (Interface Segregation Principle): interface yang besar harus di pecah jadi lebih spesifik dan memiliki method yang relevan
     tiap interface yang ada sudah dibuat dengan ukuran yang pas dan fungsi yang relevan. ProductService dan CarService dibuat dengan memiliki fungsinya masing-masing. 
   - DIP (Dependency Inversion Principle): suatu kelas bergantung ke interface atau abstract class dibanding concrete class dan fungsi (yang high-level gaboleh bergantung ke yang low-level)
     sebelumnya CarController bergantung pada kelas ProductController namun dipisah dan dibuat menjadi kelas yang tidak dependent dengan kelas yang setingkat atau lebih rendah. 


2. kelebihan menggunakan prinsip-prinsip SOLID ke proyek:
   pengaplikasian SOLID membuat kode menjadi modular, fleksibel, dan mudah dimaintain
   contohnya pemisahan kelas-kelas entitas car (Car, CarService, CarServiceImpl, CarRepository, dan CarController) dan entitas product (Product, ProductService, ProductServiceImpl, ProductRepository, dan ProductController) mempermudah proses debugging karena tiap class sudah ada fungsinya masing-masing.


3. kekurangan jika tidak menggunakan prinsip-prinsip SOLID: 
   misal kode CarController tidak dipisah dari ProductController dan kita ingin menambah Dress maka kita harus mengubah kode ProductController dan kelasnya akan menjadi sangat panjang selain itu DressController harus bergantung pada kelas ProductController. hal ini akan menyulitkan proses pemahaman dan debugging kode. 
   kemudian, pengaplikasian SOLID juga tidak boleh berlebihan karena bisa berpotensi menimbulkan masalah lainnya misal ekstensi kelas yang terlalu banyak karena pemisahan interface yang berlebihan berdampak pada code smells. 