## Modul 2
### Refleksi

1. tidak berhasil mengintegrasikan sonarcloud untuk menganalisis repo(the main branch of this project is empty)
2. kode sudah mengimplementasikan definisi continuous integration dan continuous deployment. alasannya: 
   1. workflow ci berfungsi untuk integrasi dan pengujian saja. mulai dari checkout kode, pengaturan toolchain java, dan menjalankan unit test
   2. workflow cd sudah mengotomatiskan proses deployment yaitu login docker, build dan push image docker, lalu deploy ke koyeb
   3. ci dan cd tidak tercampur. sudah dihandle di masing-masing workflow. selain itu terdapat tambahan workflow untuk analisis apakah kode sudah maintanable atau belum
   4. tiap workflow automatis dijalankan ketika push atau pull request sehingga ketika diperbarui langsung diintegrasi dan di deploy. 