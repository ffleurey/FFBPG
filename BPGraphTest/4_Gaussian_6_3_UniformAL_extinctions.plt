# Extinction sequence for 30 applications  and 30 platforms.
set title '4_Gaussian_6_3_UniformAL_extinctions.dat: Extinction sequences (avg robustness = <AVG>%)'
set xlabel 'Platformed Killed'
set ylabel 'Applications Alive'
set xrange [0:30]
set yrange [0:30]
plot \
"4_Gaussian_6_3_UniformAL_extinctions.dat" using 2 notitle with lines lc rgb 'grey', \
"4_Gaussian_6_3_UniformAL_extinctions.dat" using 3 notitle with lines lc rgb 'grey', \
"4_Gaussian_6_3_UniformAL_extinctions.dat" using 4 notitle with lines lc rgb 'grey', \
"4_Gaussian_6_3_UniformAL_extinctions.dat" using 5 notitle with lines lc rgb 'grey', \
"4_Gaussian_6_3_UniformAL_extinctions.dat" using 6 notitle with lines lc rgb 'grey', \
"4_Gaussian_6_3_UniformAL_extinctions.dat" using 7 title 'Average' with lines lw 2 lc rgb 'red', \
"4_Gaussian_6_3_UniformAL_extinctions.dat" using 8 title 'Reference' with line lt 'dashed' lw 2 lc rgb 'black'
