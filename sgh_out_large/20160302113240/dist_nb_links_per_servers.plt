set title 'dist_nb_links_per_servers (AVG = 0.19230769230769232)'
set xlabel '# Links (from Clients)'
set ylabel '# Servers'
set xrange [0:2599]
plot "dist_nb_links_per_servers.dat" using 1 notitle with line

