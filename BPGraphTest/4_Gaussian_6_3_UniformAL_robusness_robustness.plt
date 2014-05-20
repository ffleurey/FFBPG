set title '4_Gaussian_6_3_UniformAL_robusness: Robustness index for 5 runs (avg = 24.18064516129032)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:4]
set yrange [-100:100]
plot "4_Gaussian_6_3_UniformAL_robusness_robustness.dat" using 1 notitle with line

