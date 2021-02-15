#include <bits/stdc++.h>
using namespace std;
#define ld long double
#define ll long long
#define REP(i, a, b) for (ll i = a; i < b; i++)
#define REPI(i, a, b) for (ll i = b - 1; i >= a; i--)
#define i_os ios::sync_with_stdio(0);  cin.tie(0);  cout.tie(0);
#define INF (ll)1e18 + 100
#define endl "\n"

ll const maxn = 1e9;

int main()
{
	i_os;
	ll n,m;
	cin>>n>>m;
	fstream f1,f2,f3,f4,f5;
	f1.open("input_account.txt",ios::out);
	f2.open("input_amount.txt",ios::out);
	f3.open("queries.txt",ios::out);
	f4.open("compare_account.txt",ios::out);
	f5.open("compare_amount.txt",ios::out);
	vector<pair<ll,ll>> table[10];
	ll account,amount;
	map<ll,ll> mp;

	REP(i,0,10){
		REP(j,0,n){
			ll acc = rand() % maxn + maxn*i;
			while(mp.count(acc)){
				acc = rand() % maxn + maxn*i;
			}
			ll amt = rand() % maxn;
			mp[acc] = 1;
			table[i].push_back({acc,amt});
		}
	}
	REP(i,0,10){
		sort(table[i].begin(),table[i].end());
		REP(j,0,n){
			f1 << table[i][j].first << " ";
			f2 << table[i][j].second << " ";
		}
		f1 << "\n";
		f2 << "\n";
	}
	vector<ll> size(10,0);
	REP(i,0,10){
		size[i] = table[i].size();
	}
	vector<vector<ll>> output;
	vector<ll> operations(7,0);
	ll iter = 0;

	while(iter < m){
		ll opr = rand() % 7;
		if(opr == 1){
			ll row = rand() % 10;
			if(size[row] == 0){
				continue;	
			} 
			ll col = rand() % size[row];
			ll amount = rand() % 100;
			output.push_back({1, table[row][col].first, amount});
			table[row][col].second -= amount;
			iter++;
			operations[opr]++;
		}
		else if(opr == 2){
			ll row = rand() % 10;
			if(size[row] == 0){
				continue;	
			} 
			ll col = rand() % size[row];
			ll amount = rand() % 100;
			output.push_back({2, table[row][col].first, amount});
			table[row][col].second += amount;
			iter++;
			operations[opr]++;
		}
		else if(opr == 3){
			ll row1 = rand() % 10;
			if(size[row1] == 0){
				continue;	
			} 
			ll col1 = rand() % size[row1];
			ll row2 = rand() % 10;
			if(size[row2] == 0){
				continue;	
			} 
			ll col2 = rand() % size[row2];
			ll amount = rand() % 100;
			output.push_back({3, table[row1][col1].first, table[row2][col2].first, amount});
			table[row1][col1].second -= amount;
			table[row2][col2].second += amount;
			iter++;
			operations[opr]++;
		}
		else if(opr == 4){
			if(operations[opr] > 30){
				continue;
			}
			ll acc = rand() % 10000000000;
			while(mp.count(acc)){
				acc = rand() % 10000000000;
			}
			ll amt = rand() % maxn;
			mp[acc] = 1;
			output.push_back({4, acc, amt});
			table[acc/maxn].push_back({acc,amt});
			size[acc/maxn]++;
			iter++;
			operations[opr]++;
		}
		else if(opr == 5){
			if(operations[opr] > 30){
				continue;
			}
			ll row = rand() % 10;
			if(size[row] == 0){
				continue;
			}
			ll col = rand() % size[row];
			output.push_back({5,table[row][col].first});
			pair<ll,ll> p = table[row][col];
			mp.erase(p.first);
			table[row].erase(table[row].begin() + col);
			size[row]--;
			operations[opr]++;
			iter++;
		}
		else {
			if(operations[opr] > 30){
				continue;
			}
			ll row = rand() % 10;
			if(size[row] == 0){
				continue;
			}
			ll col = rand() % size[row];
			ll newacc = rand() % 10;
			pair<ll,ll> p = table[row][col];
			if(mp.count(p.first % maxn + maxn*newacc)){
				continue;
			}
			table[newacc].push_back({p.first % maxn + maxn*newacc, p.second});
			output.push_back({6, table[row][col].first, newacc});
			table[row].erase(table[row].begin() + col);
			size[row]--;
			size[newacc]++;
			operations[opr]++;
			iter++;
		}
	}
	for(vector<ll> v : output){
		for(ll it : v){
			f3 << it << " ";
		}
		f3 << "\n";
	}
	REP(i,0,10){
		sort(table[i].begin(),table[i].end());
		for(pair<ll,ll> v : table[i]){
			f4 << v.first << " ";
			f5 << v.second << " ";
		}
		f4 << "\n";
		f5 << "\n";
	}
	f1.close();
	f2.close();
	f3.close();
	return 0;
}