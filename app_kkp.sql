-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 26, 2022 at 05:30 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `app_kkp`
--

-- --------------------------------------------------------

--
-- Table structure for table `absen_siswa`
--

CREATE TABLE `absen_siswa` (
  `kd_absen` varchar(10) NOT NULL,
  `id_status` varchar(10) NOT NULL,
  `nik` varchar(10) NOT NULL,
  `nama_siswa` varchar(10) NOT NULL,
  `sakit` int(2) NOT NULL,
  `izin` int(2) NOT NULL,
  `alpha` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `absen_siswa`
--

INSERT INTO `absen_siswa` (`kd_absen`, `id_status`, `nik`, `nama_siswa`, `sakit`, `izin`, `alpha`) VALUES
('ABS1001', 'ST1001', '210001', 'dina', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `administrasi`
--

CREATE TABLE `administrasi` (
  `no_pem` varchar(10) NOT NULL,
  `tgl_pem` varchar(10) NOT NULL,
  `nik` varchar(10) NOT NULL,
  `nama` varchar(15) NOT NULL,
  `kelas` varchar(5) NOT NULL,
  `jns_pem` varchar(15) NOT NULL,
  `biaya` varchar(100) NOT NULL,
  `total` varchar(100) NOT NULL,
  `kurang` varchar(50) NOT NULL,
  `ket` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `akhlak_siswa`
--

CREATE TABLE `akhlak_siswa` (
  `kd_akhlak` varchar(10) NOT NULL,
  `id_status` varchar(10) NOT NULL,
  `nik` varchar(10) NOT NULL,
  `nama_siswa` varchar(10) NOT NULL,
  `percaya_diri` text NOT NULL,
  `t_jawab` text NOT NULL,
  `mandiri` text NOT NULL,
  `disiplin` text NOT NULL,
  `catatan` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `akhlak_siswa`
--

INSERT INTO `akhlak_siswa` (`kd_akhlak`, `id_status`, `nik`, `nama_siswa`, `percaya_diri`, `t_jawab`, `mandiri`, `disiplin`, `catatan`) VALUES
('KP10001', 'ST1001', '210001', 'dina', 'C', 'B', 'A', 'A', 'baik');

-- --------------------------------------------------------

--
-- Table structure for table `jadwalpelajaran`
--

CREATE TABLE `jadwalpelajaran` (
  `nip_guru` varchar(10) NOT NULL,
  `nama_guru` varchar(15) NOT NULL,
  `mapel` varchar(15) NOT NULL,
  `hari` varchar(8) NOT NULL,
  `waktu_mulai` varchar(6) NOT NULL,
  `waktu_selesai` varchar(6) NOT NULL,
  `kelas` text NOT NULL,
  `semester` text NOT NULL,
  `t_ajaran` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jadwalpelajaran`
--

INSERT INTO `jadwalpelajaran` (`nip_guru`, `nama_guru`, `mapel`, `hari`, `waktu_mulai`, `waktu_selesai`, `kelas`, `semester`, `t_ajaran`) VALUES
('2345', 'indonesia', 'indonesia', 'SELASA', '09.00', '10.00', 'A', 'SATU', '2022/2023'),
('34567', 'indonesia', 'indonesia', 'RABU', '08.00', '09.00', 'A', 'SATU', '2022/2023');

-- --------------------------------------------------------

--
-- Table structure for table `mapel`
--

CREATE TABLE `mapel` (
  `kd_mapel` varchar(10) NOT NULL,
  `nama_mapel` varchar(20) NOT NULL,
  `kelas` text NOT NULL,
  `nip` varchar(12) NOT NULL,
  `wali_kelas` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `mapel`
--

INSERT INTO `mapel` (`kd_mapel`, `nama_mapel`, `kelas`, `nip`, `wali_kelas`) VALUES
('hadsjd', 'indonesia', 'A', '2345', 'jinan');

-- --------------------------------------------------------

--
-- Table structure for table `nilai_siswa`
--

CREATE TABLE `nilai_siswa` (
  `kd_nilai_mapel` varchar(11) NOT NULL,
  `status_siswa` varchar(10) NOT NULL,
  `nik` varchar(10) NOT NULL,
  `nama_siswa` varchar(10) NOT NULL,
  `Kelas` varchar(10) NOT NULL,
  `semester` varchar(10) NOT NULL,
  `tahun_ajaran` varchar(10) NOT NULL,
  `mapel` varchar(10) NOT NULL,
  `n_perkembangan` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `nilai_siswa`
--

INSERT INTO `nilai_siswa` (`kd_nilai_mapel`, `status_siswa`, `nik`, `nama_siswa`, `Kelas`, `semester`, `tahun_ajaran`, `mapel`, `n_perkembangan`) VALUES
('NM10006', ' Aktif  ', '210002', 'angel', 'A', 'satu', '2022/2023', 'indonesia', '67');

-- --------------------------------------------------------

--
-- Table structure for table `siswa`
--

CREATE TABLE `siswa` (
  `nik` varchar(15) NOT NULL,
  `nama_siswa` varchar(15) NOT NULL,
  `temp_lahir` varchar(10) NOT NULL,
  `tgl_lahir` text NOT NULL,
  `j_kelamin` varchar(10) NOT NULL,
  `agama` varchar(10) NOT NULL,
  `alamat` varchar(20) NOT NULL,
  `handphone` varchar(14) NOT NULL,
  `nama_ayah` varchar(15) NOT NULL,
  `nama_ibu` varchar(15) NOT NULL,
  `pekerjaan_ayah` varchar(15) NOT NULL,
  `pekerjaan_ibu` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `siswa`
--

INSERT INTO `siswa` (`nik`, `nama_siswa`, `temp_lahir`, `tgl_lahir`, `j_kelamin`, `agama`, `alamat`, `handphone`, `nama_ayah`, `nama_ibu`, `pekerjaan_ayah`, `pekerjaan_ibu`) VALUES
('210002', 'angel', 'jakarta', '13 May 2022', 'laki-laki', 'Hindu', 'jakarta', '453', 'joni', 'kiki', 'kantor', 'rumah'),
('210003', 'JONI', 'JAKARTA', '03 May 2022', 'laki-laki', 'Islam', 'JAKARTA', '4643', 'YUNO', 'JINU', 'APA AAJA', 'IBU');

-- --------------------------------------------------------

--
-- Table structure for table `status_siswa`
--

CREATE TABLE `status_siswa` (
  `id_status` varchar(10) NOT NULL,
  `nik` varchar(10) NOT NULL,
  `nama_siswa` varchar(15) NOT NULL,
  `kelas` varchar(10) NOT NULL,
  `status_siswa` varchar(15) NOT NULL,
  `semester` varchar(10) NOT NULL,
  `t_ajaran` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `status_siswa`
--

INSERT INTO `status_siswa` (`id_status`, `nik`, `nama_siswa`, `kelas`, `status_siswa`, `semester`, `t_ajaran`) VALUES
('ST1001', '210001', 'dina', 'A', ' Aktif  ', 'dua', '2022/2023'),
('ST1002', '210002', 'angel', 'A', ' Aktif  ', 'satu', '2022/2023');

-- --------------------------------------------------------

--
-- Table structure for table `tabel_guru`
--

CREATE TABLE `tabel_guru` (
  `nip` varchar(12) NOT NULL,
  `nama_guru` varchar(12) NOT NULL,
  `temp_lahir` varchar(12) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `j_kelamin` varchar(10) NOT NULL,
  `jabatan` varchar(10) NOT NULL,
  `alamat` varchar(15) NOT NULL,
  `telephone` varchar(15) NOT NULL,
  `username` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tabel_guru`
--

INSERT INTO `tabel_guru` (`nip`, `nama_guru`, `temp_lahir`, `tanggal_lahir`, `j_kelamin`, `jabatan`, `alamat`, `telephone`, `username`, `password`) VALUES
('34567', 'HITA', 'JAKARTA', '2022-05-02', 'Perempuan', 'admin', 'JGJGJG', '3678', 'HITTA', '123'),
('532673', 'joko', 'bali', '2022-05-05', 'Laki-laki', 'guru', 'jakarawe', '5678', 'joko', '123');

-- --------------------------------------------------------

--
-- Table structure for table `tahun_ajaran`
--

CREATE TABLE `tahun_ajaran` (
  `kd_t_ajaran` varchar(10) NOT NULL,
  `t_ajar` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tahun_ajaran`
--

INSERT INTO `tahun_ajaran` (`kd_t_ajaran`, `t_ajar`) VALUES
('TA-001', '2022/2023');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absen_siswa`
--
ALTER TABLE `absen_siswa`
  ADD PRIMARY KEY (`kd_absen`);

--
-- Indexes for table `administrasi`
--
ALTER TABLE `administrasi`
  ADD PRIMARY KEY (`no_pem`);

--
-- Indexes for table `akhlak_siswa`
--
ALTER TABLE `akhlak_siswa`
  ADD PRIMARY KEY (`kd_akhlak`);

--
-- Indexes for table `mapel`
--
ALTER TABLE `mapel`
  ADD PRIMARY KEY (`kd_mapel`);

--
-- Indexes for table `nilai_siswa`
--
ALTER TABLE `nilai_siswa`
  ADD PRIMARY KEY (`kd_nilai_mapel`);

--
-- Indexes for table `siswa`
--
ALTER TABLE `siswa`
  ADD PRIMARY KEY (`nik`);

--
-- Indexes for table `status_siswa`
--
ALTER TABLE `status_siswa`
  ADD PRIMARY KEY (`id_status`);

--
-- Indexes for table `tabel_guru`
--
ALTER TABLE `tabel_guru`
  ADD PRIMARY KEY (`nip`);

--
-- Indexes for table `tahun_ajaran`
--
ALTER TABLE `tahun_ajaran`
  ADD PRIMARY KEY (`kd_t_ajaran`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
