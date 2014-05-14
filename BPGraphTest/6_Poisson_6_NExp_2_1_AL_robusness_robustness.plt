set title '6_Poisson_6_NExp_2_1_AL_robusness: Robustness index for 25 runs (avg = 53.29493333333332)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:24]
set yrange [-100:100]
plot "6_Poisson_6_NExp_2_1_AL_robusness_robustness.dat" using 1 notitle with line

