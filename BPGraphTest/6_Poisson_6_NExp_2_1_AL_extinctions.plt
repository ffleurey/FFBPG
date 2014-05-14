# Extinction sequence for 75 applications  and 75 platforms.
set title '6_Poisson_6_NExp_2_1_AL_extinctions.dat: Extinction sequences (avg robustness = <AVG>%)'
set xlabel 'Platformed Killed'
set ylabel 'Applications Alive'
set xrange [0:75]
set yrange [0:75]
plot \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 2 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 3 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 4 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 5 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 6 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 7 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 8 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 9 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 10 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 11 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 12 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 13 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 14 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 15 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 16 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 17 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 18 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 19 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 20 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 21 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 22 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 23 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 24 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 25 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 26 notitle with lines lc rgb 'grey', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 27 title 'Average' with lines lw 2 lc rgb 'red', \
"6_Poisson_6_NExp_2_1_AL_extinctions.dat" using 28 title 'Reference' with line lt 'dashed' lw 2 lc rgb 'black'
