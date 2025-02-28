## Modul 3
### Refleksi

1. prinsip yang diterapkan: 
   - SRP (Single Responsibility Principle): suatu class harus memiliki 1 fungsi saja
     CarController dipisah dengan ProductController, begitu juga dengan CarRepository, CarService, dan CarServiceImpl yang dibuat berbeda dengan yang dimiliki product. 
   - OCP (Open-Closed Principle): terbuka untuk ekstensi tetapi tertutup untuk di modifikasi
     
   - LSP (Liskov Substitution Principle): object di superclass harus bisa di gantikan dengan objek di subclass
     
   - ISP (Interface Segregation Principle): interface yang besar harus di pecah jadi lebih spesifik dan memiliki method yang relevan
     
   - DIP (Dependency Inversion Principle): suatu kelas bergantung ke interface atau abstract class dibanding concrete class dan fungsi (yang high-level gaboleh bergantung ke yang low-level)
     sebelumnya CarController bergantung pada kelas ProductController namun dipisah dan dibuat menjadi kelas yang tidak dependent dengan kelas yang setingkat atau lebih rendah. 
 
2. kelebihan menggunakan prinsip-prinsip SOLID ke proyek:
   


3. kekurangan jika tidak menggunakan prinsip-prinsip SOLID: 
   

