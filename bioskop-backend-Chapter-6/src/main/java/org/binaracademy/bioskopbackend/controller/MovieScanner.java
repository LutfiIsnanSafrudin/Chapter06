/**
 * Class ini hanya dummy dari controller sebelumnya yang masih
 * menggunakan Scanner
 */
//package org.binaracademy.bioskopbackend.controller;
//
//import org.binaracademy.bioskopbackend.model.Movie;
//import org.binaracademy.bioskopbackend.model.response.MovieResponse;
//import org.binaracademy.bioskopbackend.service.MovieService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//
//import java.text.ParseException;
//import java.util.InputMismatchException;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.Scanner;
//
//public class MovieScanner {
//
//    //    private Logger log;
//
////    @PostConstruct
////    public void init() throws ParseException {
////        this.mainMenu();
////    }
//
//    private Scanner scanner = new Scanner(System.in);
//
//    @Autowired
//    public MovieService movieService;
//
//    public void separator() {
//        System.out.println("===============================================");
//    }
//
//    public void mainMenu() throws ParseException {
//        log.trace("Ini level trace");
//        log.debug("ini level debug");
//        log.info("Processing mainMenu() -- ini level info");
//        log.warn("ini level warn");
//        log.error("ini level error");
//        System.out.println("Welcome to Bioskop Binar!!\n" +
//                "Silahkan pilih menu\n" +
//                "1. Lihat film sedang tayang\n" +
//                "2. Tambahkan film\n" +
//                "3. Lihat semua film\n" +
//                "0. Keluar");
//        System.out.print("=> ");
//        int pilihan = scanner.nextInt();
//        scanner.nextLine();
//        switch(pilihan) {
//            case 1:
//                this.showFilmSedangTayang();
//                break;
//            case 2:
//                this.addNewMovie();
//                break;
//            case 3:
//                this.getAllMovie(null);
//                break;
//            case 0:
//                System.exit(0);
//            default:
//                System.out.println("Pilihan dengan benar coy\n!!!!!!!!!!!");
//                this.mainMenu();
//        }
//    }
//
//    public void getAllMovie(Page<Movie> movies) throws ParseException {
//        this.separator();
//        System.out.println("Berikut adalah semua film yang kami punya");
//        System.out.println("Nama Film \t | \t Sinopsis");
//        movies = Optional.ofNullable(movies)
//                .orElseGet(() -> movieService.getMoviePaged(0));
//        movies.forEach(movie -> {
//            System.out.println(movie.getName() + "\t | \t" + movie.getSynopsis());
//        });
//        this.separator();
//        System.out.println("Halaman : " + (movies.getPageable().getPageNumber() + 1));
//        System.out.println("Total halaman : " + movies.getTotalPages());
//        System.out.println("Jumlah data : " + movies.getTotalElements());
//        System.out.println("Kembali : " + movies.hasPrevious());
//        System.out.println("Lanjut : " + movies.hasNext());
//        System.out.print("Masukkan halaman yang ingin anda tuju, Ketik \"n\" jika ingin keluar => ");
//        try {
//            int pilihan = scanner.nextInt() - 1;
//            scanner.nextLine();
//            movies = movieService.getMoviePaged(pilihan);
//            this.getAllMovie(movies);
//        } catch(InputMismatchException e) {
//            this.mainMenu();
//        }
//    }
//
//    public void showFilmSedangTayang() throws ParseException {
//        this.separator();
//        System.out.println("Berikut adalah film yang sedang tayang saat ini");
//        System.out.println("Nama Film \t | \t Sinopsis");
//        List<Movie> movies = movieService.getMovieCurrentlyShowing(null);
//        movies.forEach(movie -> {
//            System.out.println(movie.getName() + "\t | \t" + movie.getSynopsis());
//        });
//        System.out.print("Pilih film yang ingin dilihat lebih detil => ");
//        int pilihan = scanner.nextInt();
//        scanner.nextLine();
//        String selectedMovie = movies.get(pilihan-1).getName();
//        this.showFilmDetail(selectedMovie);
//    }
//
//    public void showFilmDetail(String selectedMovieName) throws ParseException {
//        MovieResponse movie = movieService.getMovieDetail(selectedMovieName);
//        System.out.println("Nama Film : " + movie.getMovieName());
//        System.out.println("Poster Image : " + movie.getPosterImage());
//        System.out.println("Sinopsis : " + movie.getSynopsis());
//        System.out.println("==========================================");
//        System.out.println("Studio : " + movie.getSchedules().stream()
//                .filter(Objects::nonNull).findFirst()
//                .map(schedule -> schedule.getStudio().getStudioName())
//                .orElse("Studio tidak ditemukan"));
//        System.out.println("Jadwal yang tersedia : ");
//        // TODO: bikin fetch to schedule
//        System.out.println("No. Mulai \t | \t Selesai");
//        movie.getSchedules().forEach(schedule -> {
//            System.out.println(movie.getSchedules().indexOf(schedule) + ". " + schedule.getStartTime() + " \t | \t " + schedule.getEndTime());
//        });
//        System.out.println();
//        System.out.println("1. Kembali ke menu utama");
//        System.out.println("2. Edit Detail Film");
//        System.out.println("3. Hapus Film Ini");
//        System.out.println("0. Keluar");
//        System.out.print("=> ");
//        int pilihan = scanner.nextInt();
//        if(pilihan == 1) {
//            this.mainMenu();
//        }
//        System.exit(0);
//    }
//
//    public void addNewMovie() throws ParseException {
//        System.out.print("Nama Film : ");
//        String movieName = scanner.nextLine();
//        System.out.print("Poster Image : ");
//        String urlImagePoster = scanner.nextLine();
//        System.out.print("Jadwal Tayang : ");
//        String schedule = scanner.nextLine();
//        System.out.print("Seat tersedia : ");
//        String seat = scanner.nextLine();
//        System.out.print("Sinopsis : ");
//        String synopsis = scanner.nextLine();
//
//        Movie newMovie = Movie.builder()
//                .name(movieName)
//                .posterImage(urlImagePoster)
////                .schedule(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(schedule))
////                .seat(seat)
//                .synopsis(synopsis)
//                .build();
//        movieService.addNewMovie(newMovie);
//        System.out.println("\nFilm baru berhasil di tambahkan!");
//        this.mainMenu();
//    }
//
//    public void updateMovie(String selectedMovieName) {
//        // TODO : do update
//    }
//
//    public void deleteMovie(String selectedMovieName) {
//        // TODO: do delete movie by movie name
//    }
//}
