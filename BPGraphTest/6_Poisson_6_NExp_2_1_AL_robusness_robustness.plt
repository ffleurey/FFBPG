set title '6_Poisson_6_NExp_2_1_AL_robusness: Robustness index for 5 runs (avg = 20.017204301075267)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:4]
set yrange [-100:100]
plot "6_Poisson_6_NExp_2_1_AL_robusness_robustness.dat" using 1 notitle with line

