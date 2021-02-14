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
	fstream f1,f2,f3;
	f1.open("sorted_account.txt",ios::in);
	f2.open("sorted_amount.txt",ios::in);
	f3.open("queries.txt",ios::out);
	vector<pair<ll,ll>> table[10];
	ll account,amount;
	while(!f1.eof() && !f2.eof()){
		f1 >> account;
		f2 >> amount;
		table[account/maxn].push_back({account,amount});
	}
	vector<ll> size(10,10);
	vector<vector<ll>> output;
	vector<ll> operations(7,0);
	ll iter = 0;

	while(iter < 1000000){
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
			output.push_back({4, acc});
			table[acc/maxn].push_back({acc,0});
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
			output.push_back({6, table[row][col].first, newacc});
			pair<ll,ll> p = table[row][col];
			table[row].erase(table[row].begin() + col);
			size[row]--;
			table[newacc].push_back({p.first % maxn + maxn*newacc, p.second});
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
	f1.close();
	f2.close();
	f3.close();
	return 0;
}