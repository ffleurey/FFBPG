set title '5_Gaussian_6_3_NExp_2_1_AL_robusness: Robustness index for 5 runs (avg = 62.0516129032258)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:4]
set yrange [-100:100]
plot "5_Gaussian_6_3_NExp_2_1_AL_robusness_robustness.dat" using 1 notitle with line

