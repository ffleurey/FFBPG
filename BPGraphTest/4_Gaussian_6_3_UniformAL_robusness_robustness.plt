set title '4_Gaussian_6_3_UniformAL_robusness: Robustness index for 25 runs (avg = 58.5077894736842)'
set xlabel 'Run'
set ylabel 'Robusness index (%)'
set xrange [0:24]
set yrange [-100:100]
plot "4_Gaussian_6_3_UniformAL_robusness_robustness.dat" using 1 notitle with line

