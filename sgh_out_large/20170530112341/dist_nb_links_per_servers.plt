set title 'dist_nb_links_per_servers (AVG = 0.3411669367909238)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:2467]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

